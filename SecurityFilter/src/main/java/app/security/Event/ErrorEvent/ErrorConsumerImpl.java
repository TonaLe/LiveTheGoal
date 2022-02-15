package app.security.Event.ErrorEvent;

import app.security.Config.AppConfigs;
import app.security.DTO.ErrorDto;
import app.security.Service.ErrorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Properties;

import static app.security.Config.AppConfigs.ERROR_TOPIC;
import static app.security.Utils.StringUtils.convertJsonToErrorDto;

@Component
public class ErrorConsumerImpl implements ErrorConsumer{

    private final Properties PROPERTIES = new Properties();
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ErrorService errorService;

    @Override
    public Properties getProperties() {
        LOG.info(String.format("Initializing consumer properties for %s: \n ", this.getClass().getName()));

        PROPERTIES.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVERS);
        PROPERTIES.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPERTIES.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPERTIES.put(ConsumerConfig.GROUP_ID_CONFIG, "Group1");
        PROPERTIES.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        PROPERTIES.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return PROPERTIES;
    }

    @Override
    public void consumeMessage(final KafkaConsumer<String, String> kafkaConsumer) {
        LOG.info("Start Consuming:     \n");

        while (true) {
            try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1000));
                for (ConsumerRecord<String, String> record : records) {
                    LOG.info( "value: " + record.value() + " topic: " + record.topic() + " offset: " +
                            record.offset() + " partition: " + record.partition());

                    if (record.topic().equals(ERROR_TOPIC)) {

                        if (StringUtils.isEmpty(record.topic())) {
                            continue;
                        }

                        final ErrorDto errorDto = convertJsonToErrorDto(record.value());
                        errorService.initErrorList(errorDto, Integer.parseInt(errorDto.getId()));
                    }
                }
                kafkaConsumer.commitSync();
            }  catch (Exception e) {
                LOG.error(String.valueOf(new IllegalArgumentException("Error because of:   " + e)));
            }
        }
    }
}