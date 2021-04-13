package br.com.alura.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

       try ( var dispatcher = new KafkaDispatcher()) {


           for (var i = 0; i < 10; i++) {
               var key = UUID.randomUUID().toString();
               var value = "12345, 12345, 8984023,33";

               dispatcher.send("ECOMERCE_NEW_ORDER", key, value);

               var email = "Thank you for you order, We are processing you order";
               dispatcher.send("ECOMERCE_SEND_EMAIL", key, email);
           }
       }
    }
}
