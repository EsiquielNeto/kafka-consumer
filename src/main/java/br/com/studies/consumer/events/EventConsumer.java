package br.com.studies.consumer.events;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class EventConsumer {

    private final KafkaConsumer<String, String> consumer;

    public EventConsumer() {
        this.consumer = new KafkaConsumer<>(properties());
    }

    public void run() {
        consumer.subscribe(Collections.singleton("mytopic"));

        log.info("iniciando consumidor...");

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                log.info("\n>>> Encontrados {} registros <<<", records.count());

                records.forEach(record ->
                        log.info("\n>>>" +
                                "\nTopic {}" +
                                "\nPartition {}" +
                                "\nOffset {}" +
                                "\nKey {}" +
                                "\nMessage {}" +
                                "\n<<<", record.topic(), record.partition(), record.offset(), record.key(), record.value())
                );
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "dafult");
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return properties;
    }
}
