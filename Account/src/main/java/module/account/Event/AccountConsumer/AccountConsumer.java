package module.account.Event.AccountConsumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public interface AccountConsumer {

    Properties getProperties();

    void consumeMessage(final KafkaConsumer<String, String> kafkaConsumer);
}
