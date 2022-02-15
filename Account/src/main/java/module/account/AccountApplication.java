package module.account;

import module.account.Event.AccountConsumer.AccountConsumer;
import module.account.Config.AppConfigs;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class AccountApplication implements ApplicationRunner {

    @Autowired
    private AccountConsumer accountConsumer;

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        Properties properties = accountConsumer.getProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(AppConfigs.ACCOUNT_CREATION_TOPIC));
        accountConsumer.consumeMessage(consumer);
    }
}
