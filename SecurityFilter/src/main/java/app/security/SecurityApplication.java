package app.security;

import app.security.Config.AppConfigs;
import app.security.Event.ErrorEvent.ErrorConsumer;
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
    private ErrorConsumer errorConsumer;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        Properties properties = errorConsumer.getProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(AppConfigs.ERROR_TOPIC));
        errorConsumer.consumeMessage(consumer);
    }
}
