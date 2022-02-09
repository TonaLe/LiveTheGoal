package app.security.Event.AccountEvent;

import app.security.Config.AppConfigs;
import app.security.Entity.Account;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class AccountProducer implements AccountEventProducer{

    private final Properties properties;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public AccountProducer() {
        this.properties = new Properties();
    }

    private void initProperties() {
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, Integer.toString(-1));
    }

    @Override
    public void sendMessage(final Account account) {
        initProperties();
        final KafkaProducer<String, Account> accountKafkaProducer = new KafkaProducer<String, Account>(properties);

    }
}
