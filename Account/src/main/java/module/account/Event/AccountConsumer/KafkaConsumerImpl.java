package module.account.Event.AccountConsumer;

import module.account.Config.AppConfigs;
import module.account.DTO.AccountDto;
import module.account.DTO.ErrorDto;
import module.account.Service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Properties;

import static module.account.Config.AppConfigs.ACCOUNT_CREATION_TOPIC;
import static module.account.Utils.StringUtils.convertJsonToAccount;

@Service
public class KafkaConsumerImpl implements AccountConsumer{

    private final Properties PROPERTIES = new Properties();
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

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
                final ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1000));
                for (ConsumerRecord<String, String> record : records) {
                    LOG.info( "value: " + record.value() + " topic: " + record.topic() + " offset: " +
                            record.offset() + " partition: " + record.partition());

                    if (record.topic().equals(ACCOUNT_CREATION_TOPIC)) {

                        if (StringUtils.isEmpty(record.topic())) {
                            return;
                        }

                        final AccountDto accountDto = convertJsonToAccount(record.value());

                        final ErrorDto errorDto = accountService.validateAccountDto(accountDto);

                        if (errorDto != null) {
                            accountService.sendErrorMsg(errorDto);
                            kafkaConsumer.commitSync();
                        }
                        accountService.setAccount(accountDto);
                    }
                }
                kafkaConsumer.commitSync();
            }  catch (Exception e) {
                LOG.error(String.valueOf(new IllegalArgumentException("Error because of:   " + e)));
            }
        }
    }
}
