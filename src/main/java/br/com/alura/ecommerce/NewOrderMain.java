package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        var value = "12345, 12345, 8984023,33";
        var email = "Thank you for you order, We are processing you order";
        var record = new ProducerRecord<>("ECOMERCE_NEW_ORDER", value, value);
        var emailRecord = new ProducerRecord<>("ECOMERCE_SEND_EMAIL", email, email);

        //Produção de Mensagens
        Callback callback = (data, ex) -> {
            if(ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println("Enviando esse topico:"+data.topic() + ":::" + data.partition() +"/OffSet:"+ data.offset() +"/TimeStamp:"+ data.timestamp());
        };

        producer.send(record, callback).get();
        producer.send(emailRecord, callback).get();

    }

    //Configurações Kafka
    private static Properties properties() {
        var properties = new Properties();
        //Endereço de onde está rodando Kafka
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //Conversor String para bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
