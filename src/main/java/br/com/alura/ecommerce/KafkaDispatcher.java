package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    KafkaDispatcher(){
       this.producer = new KafkaProducer<>(properties());
    }

    //Configurações Kafka
    private static Properties properties() {
        var properties = new Properties();
        //Endereço de onde está rodando Kafka
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //Conversor String para bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

        return properties;
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {

        //Produção de Mensagens
        var record = new ProducerRecord<>(topic, key, value);
        Callback callback = (data, ex) -> {

            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Enviando esse topico:" + data.topic() + ":::" + data.partition() + "/OffSet:" + data.offset() + "/TimeStamp:" + data.timestamp());
        };
        producer.send(record, callback).get();
    }

    @Override
    public void close() {
        producer.close();
    }
}
