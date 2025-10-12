package org.cibertec.service;


import org.cibertec.entity.auditoria.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class MessageConsumerService {

    @RabbitListener(queues = "${app.queue.name}")
    public void receiveMessage(String message) {
        try {
            // Convertimos el JSON recibido a objeto Log
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            Log log = mapper.readValue(message, Log.class);

           
            System.out.println("------------------------------------------------");
            System.out.println("Mensaje recibido desde RabbitMQ:");
            System.out.println("Log ID: " + log.getLogId());
            System.out.println("Mensaje: " + log.getMessage());
            System.out.println("Fecha Log: " + log.getLogDate());
            System.out.println("JSON recibido : " + message);
            System.out.println("------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas al consumir el mensaje: " + e.getMessage());
        }
    }
}
