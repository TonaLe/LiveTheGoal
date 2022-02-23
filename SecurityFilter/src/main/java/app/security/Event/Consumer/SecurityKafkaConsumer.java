package app.security.Event.Consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * The interface Security kafka consumer.
 */
public interface SecurityKafkaConsumer {

    /**
     * Gets properties.
     *
     * @return the properties
     */
    Properties getProperties();

    /**
     * Consume message.
     *
     * @param kafkaConsumer the kafka consumer
     */
    void consumeMessage(final KafkaConsumer<String, String> kafkaConsumer);
}
