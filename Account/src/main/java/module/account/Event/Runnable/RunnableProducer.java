package module.account.Event.Runnable;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Account runnable producer.
 */
public class RunnableProducer implements Runnable {

    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /**
     * The Id.
     */
    private final String id;
    /**
     * The Topic name.
     */
    private final String topicName;
    /**
     * The Producer.
     */
    private final KafkaProducer<String, String> producer;
    /**
     * The Stopper.
     */
    private final AtomicBoolean STOPPER = new AtomicBoolean(false);

    /**
     * The Json object.
     */
    private final String jsonObject;

    /**
     * Instantiates a new runnable producer.
     *
     * @param id          the id
     * @param topicName   the topic name
     * @param producer    the producer
     * @param jsonObject the stringify object
     */
    public RunnableProducer(final String id, final String topicName, final KafkaProducer<String, String> producer,
                                   final String jsonObject) {
        this.id = id;
        this.topicName = topicName;
        this.producer = producer;
        this.jsonObject = jsonObject;
    }

    @Override
    public void run() {
        try {
            LOG.info("Starting Account producer - " + id);
            producer.send(new ProducerRecord<>(topicName, id, jsonObject));
        } catch (Exception e) {
            LOG.error(String.valueOf(new IllegalArgumentException("Exception in producer - " + id)));
            throw new RuntimeException(e);
        }
    }

    /**
     * Shutdown.
     */
    public void shutdown() {
        LOG.info("Shutting down producer - " + id);
        STOPPER.set(true);
    }
}
