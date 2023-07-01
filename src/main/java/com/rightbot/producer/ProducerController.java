package com.rightbot.producer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProducerController {
    Logger logger = LoggerFactory.getLogger(ProducerController.class.getName());
    // object of rabbitmq template
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private AllProducer allProducer;


    @PostMapping("/rabbitmq/initialize")
    @ResponseBody
    public ResponseEntity<String> initializeRMQ(@RequestBody String message) {
        allProducer.readConfig();
        return ResponseEntity.ok("response sent: " + message);
    }

    @PostMapping("/rabbitmq/publish")
    @ResponseBody
    public ResponseEntity<String> publishMessageToRabbitMQ( @RequestBody JsonNode message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode messageJson = objectMapper.readTree(message.toString());
        logger.info("messageJson",messageJson);

        String topicName = messageJson.get("topic_name").asText();
        logger.info("topicName {}",topicName);
        JsonNode Message = messageJson;
        logger.info("Message {}",Message);

        allProducer.publishMessage(topicName, Message);

        logger.info("Sent post response for  topic {}: {}", "topicName", message);
        return ResponseEntity.ok("Message published successfully");
    }


    @GetMapping("/mocked-api")
    public ResponseEntity<Map<String, Object>> getRadarData() throws IOException {
        ClassPathResource staticDataResource = new ClassPathResource("Agent-Config.json");
        String staticDataString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);
        logger.info("getting the staticDataString: ", staticDataString);
        allProducer.readConfig();
        return new ResponseEntity<>(
                new JSONObject(staticDataString).toMap(),
                HttpStatus.OK
        );
    }
}

