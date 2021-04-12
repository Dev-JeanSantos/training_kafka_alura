package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumeFunction {
    void consume(ConsumerRecord<String, String> record);
}
