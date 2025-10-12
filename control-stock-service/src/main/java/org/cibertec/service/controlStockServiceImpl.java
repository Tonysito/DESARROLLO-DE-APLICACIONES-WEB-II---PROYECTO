package org.cibertec.service;

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

    @Override
    public List<ProductoBajoStock> actualizarReporteBajoStock() {
        // Traemos productos con stock menor a 5 del microservicio
        List<Producto> productosBajos = productoClient.getProductosBajoStock();

        for (Producto p : productosBajos) {
            // Buscamos si ya existe en la tabla de bajo stock
            ProductoBajoStock registro = controlStockRepository.findByIdproducto(p.getIdProducto())
                    .orElse(new ProductoBajoStock());

            // Seteamos datos
            registro.setIdproducto(p.getIdProducto());
            registro.setNombre(p.getNombre());
            registro.setStock(p.getStock());
            registro.setPrecio(p.getPrecio());
            registro.setFechareporte(LocalDateTime.now());

            // Guardamos en la base de datos
            controlStockRepository.save(registro);
            // Guardar un único log indicando que la lista fue actualizada
        
        
        }   
    registrarLogYEnviar("Tabla de reportes actualizada");
    return controlStockRepository.findAll();
    }

    @Override
    public List<ProductoBajoStock> listaBajoStock() {
        return controlStockRepository.findAll();
    }

    /**
     * Método centralizado que guarda el log y envía el mensaje a RabbitMQ
     */
    private void registrarLogYEnviar(String mensaje) {
        Log log = new Log();
        log.setLogDate(LocalDateTime.now());
        log.setMessage(mensaje);

        // Guardar en la base de datos y enviar mensaje a rabbit
        logRepository.save(log);
        messageProducerService.sendMessage(log);
     
    }
}
