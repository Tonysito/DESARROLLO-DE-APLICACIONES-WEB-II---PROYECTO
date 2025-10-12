package org.cibertec.service;


import org.cibertec.entity.auditoria.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class MessageProducerService {

	@Value("${app.queue.name}")
	private String queueName;
	
	
  
    
	@Autowired
	private RabbitTemplate rabbitTemplate;
	  private static int logCounter = 1; //para asignarle id a los mensajes
	

	public void sendMessage(Log log) {
		try {

			
			 log.setLogId(logCounter++);
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			
		
			String message = mapper.writeValueAsString(log);
	
			rabbitTemplate.convertAndSend("", queueName, message);
			System.out.println("Mensaje enviado: " + message);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problemas al enviar el mensaje. " + e.getMessage());
		}
	}
	
}
