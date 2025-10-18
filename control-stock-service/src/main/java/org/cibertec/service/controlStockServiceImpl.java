package org.cibertec.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.cibertec.client.ProductoClient;
import org.cibertec.entity.Producto;
import org.cibertec.entity.auditoria.Log;
import org.cibertec.entity.auditoria.ProductoBajoStock;
import org.cibertec.repository.ControlStockRepository;
import org.cibertec.repository.auditoria.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;

@Service
public class controlStockServiceImpl implements controlStockService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private ControlStockRepository controlStockRepository;

    @Autowired
    private MessageProducerService messageProducerService;

    // --------------------- SERVICIOS PRINCIPALES ---------------------
    
    @Transactional
    @Override
    @CircuitBreaker(name = "stockCB", fallbackMethod = "fallbackActualizarReporteBajoStock")
    @Retry(name = "stockCB")
    public List<ProductoBajoStock> actualizarReporteBajoStock() {
        List<Producto> productosBajos = productosBajosClient();
        
        for (Producto p : productosBajos) {
            ProductoBajoStock registro = controlStockRepository.findByIdproducto(p.getIdProducto())
                    .orElse(new ProductoBajoStock());

            registro.setIdproducto(p.getIdProducto());
            registro.setNombre(p.getNombre());
            registro.setStock(p.getStock());
            registro.setPrecio(p.getPrecio());
            registro.setFechareporte(LocalDateTime.now());

            controlStockRepository.save(registro);
        }

        registrarLogYEnviar("Tabla de reportes de bajo stock actualizada");

        return listaBajoStock();
    }

 // --------------------- FALLBACK ---------------------
    public List<ProductoBajoStock> fallbackActualizarReporteBajoStock(Throwable ex) {
        System.err.println("Fallo al actualizar reporte de bajo stock. Fallback activado: " + ex.getMessage());
        
        // Creamos un objeto ProductoBajoStock "vacío" con mensaje
        ProductoBajoStock error = new ProductoBajoStock();
        error.setIdproducto(-1);
        error.setNombre("No se pudo actualizar el reporte");
        error.setStock(0);
        error.setPrecio(BigDecimal.ZERO);

        // Retornamos la lista con un único objeto de error
        return List.of(error);
    }


    // --------------------- LISTA DE STOCK ---------------------
    //PRUEBA: APAGAR MYSQL O ELIMINAR TABLA controlStockProducto

    @CircuitBreaker(name = "stockCB", fallbackMethod = "fallbackListaBajoStock")
    @Retry(name = "stockCB")
    @Override
    public List<ProductoBajoStock> listaBajoStock() {
        return controlStockRepository.findAll();
    }

    public List<ProductoBajoStock> fallbackListaBajoStock(Throwable ex) {
        System.err.println("Fallo al obtener lista de productos bajo stock. Fallback activado: " + ex.getMessage());

        ProductoBajoStock vacio = new ProductoBajoStock();
        vacio.setIdproducto(-1);
        vacio.setNombre("No disponible");
        vacio.setStock(0);
        vacio.setPrecio(BigDecimal.ZERO);

        return List.of(vacio);
    }

    // --------------------- LISTA DE PRODUCTOS DEL MICROSERVICIO ---------------------
    //PRUEBA: APAGAR MICROSERVICIO producto-service
    @CircuitBreaker(name = "stockCB", fallbackMethod = "fallbackProductosBajosClient")
    @Retry(name = "stockCB")
    public List<Producto> productosBajosClient() {
        return productoClient.getProductosBajoStock();
    }

    public List<Producto> fallbackProductosBajosClient(Throwable ex) {
        Producto vacio = new Producto();
        vacio.setIdProducto(-1);
        vacio.setNombre("Microservicio apagado o MySQL no disponible");
        vacio.setStock(0);
        vacio.setActivo(false);
        vacio.setPrecio(BigDecimal.ZERO);

        System.err.println("Fallo al obtener productos desde client. Fallback activado: " + ex.getMessage());
        return List.of(vacio);
    }

    // --------------------- LOGS Y AUDITORIA ---------------------

  
    public void registrarLogYEnviar(String mensaje) {
        // Guardamos log en BD siempre
        Log log = new Log();
        log.setLogDate(LocalDateTime.now());
        log.setMessage(mensaje);
        logRepository.save(log);

        // Intentamos enviar a RabbitMQ en un objeto separado
        Log logParaRabbit = new Log();
        logParaRabbit.setLogDate(log.getLogDate());
        logParaRabbit.setMessage(log.getMessage());

        enviarLogRabbit(logParaRabbit);
    }

    public void fallbackregistrarLogYEnviar(String mensaje, Throwable ex) {
        System.err.println("Fallback activado: no se pudo enviar log a RabbitMQ. Mensaje: " + mensaje);
        System.err.println("Causa: " + ex.getMessage());
    }

    // --------------------- ENVIO DE LOG A RABBITMQ ---------------------
  //PRUEBA: APAGAR SERVICIO DE RABBITMQ
    @CircuitBreaker(name = "stockCB", fallbackMethod = "fallbackenviarLogRabbit")
    @Retry(name = "stockCB")
    public void enviarLogRabbit(Log log) {
        messageProducerService.sendMessage(log);
    }
    
    

    public void fallbackenviarLogRabbit(Log logOriginal, Throwable ex) {
        Log fallbackLog = new Log(); 
        fallbackLog.setLogDate(LocalDateTime.now());
        fallbackLog.setMessage(logOriginal.getMessage() + " (Fallo envío a RabbitMQ)");

        logRepository.save(fallbackLog); // guardamos el log en BD aunque falle Rabbit

        System.err.println("Fallback RabbitMQ activado: " + fallbackLog.getMessage());
        System.err.println("Causa: " + ex.getMessage());
    }
    
    

    


}



