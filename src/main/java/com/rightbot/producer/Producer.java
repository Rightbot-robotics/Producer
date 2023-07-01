package com.rightbot.producer;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producer {
    Logger logger = LoggerFactory.getLogger(Producer.class.getName());
    private String Topic;
    private JsonNode Schema;
    public Producer(String Topic, JsonNode schema) {
        this.Topic = Topic;
        this.Schema = schema;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public JsonNode getSchema() {
        return Schema;
    }

    public void setSchema(JsonNode schema) {
        Schema = schema;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "Topic='" + Topic + '\'' +
                ", Schema=" + Schema +
                '}';
    }
}
