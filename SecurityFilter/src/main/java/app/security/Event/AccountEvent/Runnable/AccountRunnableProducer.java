package app.security.Event.AccountEvent.Runnable;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Account runnable producer.
 */
public class AccountRunnableProducer implements Runnable{

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /**
     * The Id.
     */
    private int id;
    /**
     * The Topic name.
     */
    private String topicName;
    /**
     * The Producer.
     */
    private KafkaProducer<String, String> producer;
    /**
     * The Stopper.
     */
    private final AtomicBoolean STOPPER = new AtomicBoolean(false);

    /**
     * The Stringify account.
     */
    private final String stringifyAccount;

    /**
     * Instantiates a new Account runnable producer.
     *
     * @param id               the id
     * @param topicName        the topic name
     * @param producer         the producer
     * @param stringifyAccount the stringify account
     */
    public AccountRunnableProducer(final int id, final String topicName, final KafkaProducer<String, String> producer,
                                   final String stringifyAccount) {
        this.id = id;
        this.topicName = topicName;
        this.producer = producer;
        this.stringifyAccount = stringifyAccount;
    }

    @Override
    public void run() {
        try {
            LOG.info("Starting Account producer - " + id);
            while (!STOPPER.get()) {
                producer.send(new ProducerRecord<>(topicName, Integer.toString(id), stringifyAccount));
            }
        } catch (Exception e) {
            LOG.error("Exception in Account producer - " + id);
            throw new RuntimeException(e);
        }
    }

    /**
     * Shutdown.
     */
    void shutdown() {
        LOG.info("Shutting down Account producer - " + id);
        STOPPER.set(true);
    }
}
