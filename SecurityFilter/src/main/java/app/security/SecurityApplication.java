package app.security;

import app.security.Config.AppConfigs;
import app.security.Event.Consumer.SecurityKafkaConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class SecurityApplication implements ApplicationRunner {

    @Autowired  
    private SecurityKafkaConsumer securityKafkaConsumer;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        Properties properties = securityKafkaConsumer.getProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(AppConfigs.ERROR_TOPIC, AppConfigs.LIST_ACCOUNT_INFO_TOPIC));
        securityKafkaConsumer.consumeMessage(consumer);
    }
}
