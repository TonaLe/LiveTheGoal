package app.security.Event.ErrorEvent;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * The interface Error consumer.
 */
public interface ErrorConsumer {

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
