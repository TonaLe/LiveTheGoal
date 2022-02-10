package app.security.Event.AccountEvent.AccountProducer;

import app.security.Config.AppConfigs;
import app.security.Entity.Account;
import app.security.Event.AccountEvent.AccountProducer.Runnable.AccountRunnableProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static app.security.Utils.StringUtils.convertObjectToString;

@Service
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
        final KafkaProducer<String, String> accountKafkaProducer = new KafkaProducer<String, String>(properties);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        AccountRunnableProducer accountRunnableProducer =new AccountRunnableProducer(account.getId(), AppConfigs.ACCOUNT_TOPIC,
                accountKafkaProducer, convertObjectToString(account));
        executor.submit(accountRunnableProducer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                accountRunnableProducer.shutdown();
                executor.shutdown();
                LOG.info("Closing Executor Service");
                try {
                    executor.awaitTermination(1000 * 2, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        ));
    }
}
