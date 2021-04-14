package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

       try ( var orderDispatcher = new KafkaDispatcher<Order>()) {
           try ( var emailDispatcher = new KafkaDispatcher<String>()) {


           for (var i = 0; i < 10; i++) {
               var userId = UUID.randomUUID().toString();
               var orderId = UUID.randomUUID().toString();
               var amount = new BigDecimal(Math.random() * 5000 + 1);

               var order = new Order(userId, orderId, amount);

               orderDispatcher.send("ECOMERCE_NEW_ORDER", userId, order);

               var email = "Thank you for you order, We are processing you order";
               emailDispatcher.send("ECOMERCE_SEND_EMAIL", userId, email);
           }
           }
       }
    }
}
