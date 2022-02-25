package app.security.Event.Consumer;

import app.security.Config.AppConfigs;
import app.security.DTO.AccountDto;
import app.security.DTO.ErrorDto;
import app.security.Service.AccountService;
import app.security.Service.Impl.ErrorService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static app.security.Config.AppConfigs.ERROR_TOPIC;
import static app.security.Utils.StringUtils.convertJsonToErrorDto;
import static app.security.Utils.StringUtils.convertJsonToListAccount;

/**
 * The type Security consumer.
 */
@Component
public class SecurityConsumerImpl implements SecurityKafkaConsumer {

    /**
     * The Properties.
     */
    private final Properties PROPERTIES = new Properties();
    /**
     * The Log.
     */
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /**
     * The Set key consumer.
     */
    private final Set<String> setKeyConsumer = new HashSet<>();

    /**
     * The Error service.
     */
    private final ErrorService errorService;

    /**
     * The Account service.
     */
    private final AccountService accountService;

    /**
     * Instantiates a new Security consumer.
     *
     * @param errorService   the error service
     * @param accountService the account service
     */
    @Autowired
    public SecurityConsumerImpl(final ErrorService errorService, final AccountService accountService) {
        this.errorService = errorService;
        this.accountService = accountService;
    }

    @Override
    public Properties getProperties() {
        LOG.info(String.format("Initializing consumer properties for %s: \n ", this.getClass().getName()));

        PROPERTIES.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.BOOTSTRAP_SERVERS);
        PROPERTIES.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPERTIES.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPERTIES.put(ConsumerConfig.GROUP_ID_CONFIG, "Group1");
        PROPERTIES.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        PROPERTIES.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        PROPERTIES.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "20000");
        PROPERTIES.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        PROPERTIES.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        return PROPERTIES;
    }

    @Override
    public void consumeMessage(final KafkaConsumer<String, String> kafkaConsumer) {
        LOG.info("Start Consuming:     \n");

        while (true) {
            try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(500));
                for (ConsumerRecord<String, String> record : records) {
                    LOG.info( "value: " + record.value() + " topic: " + record.topic() + " offset: " +
                            record.offset() + " partition: " + record.partition());

                    final String HYPHEN = "_";
                    final String keyPartition = StringUtils.join(HYPHEN, record.partition(),
                            record.key(), record.offset());

                    if (record.topic().equals(ERROR_TOPIC)) {

                        if (StringUtils.isEmpty(record.value()) || setKeyConsumer.contains(keyPartition)) {
                            continue;
                        }

                        final ErrorDto errorDto = convertJsonToErrorDto(record.value());
                        setKeyConsumer.add(keyPartition);
                        errorService.initErrorList(errorDto, record.key());
                    } else if (record.topic().equals(AppConfigs.LIST_ACCOUNT_INFO_TOPIC)) {
                        if (StringUtils.isBlank(record.value())) {
                            continue;
                        }

                        final List<AccountDto> listAccount = convertJsonToListAccount(record.value());
                        accountService.initAccountList(listAccount, keyPartition);
                    }
                }
                kafkaConsumer.commitSync();
            }  catch (Exception e) {
                LOG.error(String.valueOf(new IllegalArgumentException("Error because of:   " + e)));
            }
        }
    }
}
