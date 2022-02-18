package module.account.Event.ErrorProducer;

import module.account.Config.AppConfigs;
import module.account.DTO.ErrorDto;
import module.account.Event.Runnable.RunnableProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static module.account.Utils.StringUtils.convertObjectToString;

/**
 * The type Error producer imp.
 */
@Component
public class ErrorProducerImp implements ErrorProducer{

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
     * Instantiates a new Error producer imp.
     */
    public ErrorProducerImp() {
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
    public void sendMessage(final ErrorDto errorDto, final int id) {
        initProperties();
        final KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        RunnableProducer runnableProducer = new RunnableProducer(String.valueOf(id),
                AppConfigs.ERROR_TOPIC, kafkaProducer, convertObjectToString(errorDto));
        executor.submit(runnableProducer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            runnableProducer.shutdown();
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
