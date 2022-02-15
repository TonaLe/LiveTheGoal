package module.account.Event.AccountProducer;


import module.account.Config.AppConfigs;
import module.account.DTO.AccountDto;
import module.account.Event.Runnable.RunnableProducer;
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

import static module.account.Utils.StringUtils.convertObjectToString;


/**
 * The type Account producer.
 */
@Service
public class AccountProducer implements AccountEventProducer {

    /**
     * The Properties.
     */
    private final Properties properties;

    /**
     * The Executor.
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * Instantiates a new Account producer.
     */
    public AccountProducer() {
        this.properties = new Properties();
    }

    /**
     * Init properties.
     */
    private void initProperties() {
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, Integer.toString(-1));
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    }

    @Override
    public void sendCreationMessage(final AccountDto account) {
        initProperties();
        final KafkaProducer<String, String> accountKafkaProducer = new KafkaProducer<String, String>(properties);
        RunnableProducer accountRunnableProducer = new RunnableProducer(account.getId(),
                AppConfigs.ACCOUNT_CREATION_TOPIC, accountKafkaProducer, convertObjectToString(account));
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

    @Override
    public void sendAuthoriseMessage(final AccountDto account) {
        initProperties();
        final KafkaProducer<String, String> accountKafkaProducer = new KafkaProducer<String, String>(properties);
        RunnableProducer accountRunnableProducer = new RunnableProducer(account.getId(),
                AppConfigs.ACCOUNT_CREATION_TOPIC, accountKafkaProducer, convertObjectToString(account));
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
