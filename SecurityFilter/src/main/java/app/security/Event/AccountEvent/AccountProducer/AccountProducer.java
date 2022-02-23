package app.security.Event.AccountEvent.AccountProducer;

import app.security.Config.AppConfigs;
import app.security.DTO.AccountDto;
import app.security.DTO.OffsetBasedPageRequest;
import app.security.Event.AccountEvent.AccountProducer.Runnable.AccountRunnableProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static app.security.Utils.StringUtils.convertObjectToString;

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
    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * The Account key topic.
     */
    private final String ACCOUNT_KEY_TOPIC = "Account_Info";

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
        properties.put(ProducerConfig.ACKS_CONFIG, Integer.toString(0));
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
    }

    @Override
    public void sendCreationMessage(final AccountDto account) {
        initProperties();
        final KafkaProducer<String, String> accountKafkaProducer = new KafkaProducer<String, String>(properties);
        AccountRunnableProducer accountRunnableProducer = new AccountRunnableProducer(account.getUsername(),
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
        AccountRunnableProducer accountRunnableProducer = new AccountRunnableProducer(account.getUsername(),
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
    public void sendRequestForAccountInfo(final String username) {
        initProperties();
        final KafkaProducer<String, String> accountKafkaProducer = new KafkaProducer<String, String>(properties);
        AccountRunnableProducer accountRunnableProducer = new AccountRunnableProducer(username,
                AppConfigs.ACCOUNT_CREATION_TOPIC, accountKafkaProducer, username);
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
    public void sendRequestForAccountsInfo(final int limit, final int offset) {
        Pageable pageable =  new OffsetBasedPageRequest(limit, offset, Sort.unsorted());
        final KafkaProducer<String, String> accountKafkaProducer = new KafkaProducer<String, String>(properties);
        AccountRunnableProducer accountRunnableProducer = new AccountRunnableProducer(ACCOUNT_KEY_TOPIC,
                AppConfigs.LIST_ACCOUNT_OFFSET_INFO_TOPIC, accountKafkaProducer, convertObjectToString(pageable));
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
