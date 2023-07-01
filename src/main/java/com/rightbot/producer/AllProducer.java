package com.rightbot.producer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.impl.ChannelN;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.TimeoutException;

@Component
@Slf4j
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllProducer {
    Logger logger = LoggerFactory.getLogger(AllProducer.class.getName());
    private static final String EXCHANGE_NAME = "my-topic-exchange";
    private HashMap<String, Producer> producer_map = new HashMap<>();
    private Connection connection;
    private HashMap<String, Channel> channel_map = new HashMap<>();

    public Channel channel;
    public AllProducer() {
        channel = null;
    }

    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    public void readConfig() {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON data from the file
            //provides a convenient way to access a file on the classpath
            ClassPathResource staticDataResource = new ClassPathResource("Agent-Config.json");

            JsonNode rootNode = objectMapper.readTree(staticDataResource.getInputStream());

            // Get the list of topics
            JsonNode topics = rootNode.get("Topics");

            // Create a connection object
            connection = getConnectionFactory().newConnection();

            // Declare a topic exchange
            for (JsonNode topic : topics) {
                // Get the corresponding topic object
                String topicName = topic.asText();
                logger.info("Setting up producer for topic: {}", topicName);
                JsonNode topicJson = rootNode.get(topicName);

                if (topicJson != null) {
                    Producer producer = new Producer(topicName, topicJson);
                    producer_map.put(topicName, producer);

                    //create channel and declare exchange type
                    channel = connection.createChannel();
                    channel.exchangeDeclare(EXCHANGE_NAME, "topic");
                    // Bind the queue to the topic exchange
                    String routingKey = topicName;
                    String queueName = "my-queue-" + routingKey;
                    channel.queueDeclare(queueName, true, false, false, null);
                    channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

                    channel_map.put(topicName, channel);
                    Channel dummy_channel = channel_map.get(topicName);
                    if(dummy_channel != null) {
                        logger.info("channel_name: {}", dummy_channel);
                        logger.info("Created queue {} for topic {}", queueName, routingKey);
                    }
                    else {
                        logger.error("created channel is null: {}", dummy_channel);
                    }
                }
            }
        } catch (IOException | TimeoutException e) {
            logger.error("Exception occurred", e);
        }
    }
    public void publishMessage(String topicName, JsonNode message) throws IOException {
        channel = channel_map.get(topicName);

        logger.info("channel {}", channel_map.get(topicName));
        logger.info("topicName {}", topicName);
        logger.info("channel {}", channel);
        if (channel != null) {
            channel.basicPublish(EXCHANGE_NAME, topicName, null, message.toString().getBytes());
        } else {
            logger.error("Channel is null for topic {}", topicName);
        }
    }
}

