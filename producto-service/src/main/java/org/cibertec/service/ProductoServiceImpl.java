package org.cibertec.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Producto;
import org.cibertec.entity.auditoria.AuditoriaProducto;
import org.cibertec.entity.auditoria.Log;
import org.cibertec.repository.ProductoRepository;
import org.cibertec.repository.auditoria.AuditoriaProductoRepository;
import org.cibertec.repository.auditoria.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	//PRODUCTOR DE MENSAJES PARA RABBITMQ
    @Autowired
    private MessageProducerService messageProducerService;
    
    //REPOSITORIO DE LOG 
    @Autowired
    private LogRepository logRepository;
    
    // AUDITORIA DE PRODDUCTO     
    @Autowired
    private AuditoriaProductoRepository auditoriaProductoRepository;

	
	@Override
	public List<Producto> findAll() {
		 return productoRepository.findAll();
	}

	
	@Override
	public List<Producto> findAllActivos() {
	    return productoRepository.findByActivoTrue();
	}
	
	
	@Override
	public List<Producto> findAllInactivos() {
	    return productoRepository.findByActivoFalse();
	}
	
	
	
	
	@Override
	public Optional<Producto> findById(Integer id) {
		  return productoRepository.findById(id);
	}

	
	
	@Override
	public Producto save(Producto producto) {
	    Producto nuevoProducto = productoRepository.save(producto);
	    registrarAuditoria(nuevoProducto, "CREADO");
	    return nuevoProducto;
	}

	@Override
	public Producto update(Producto producto) {
	    Producto productoActualizado = productoRepository.save(producto);
	    registrarAuditoria(productoActualizado, "ACTUALIZADO");
	    return productoActualizado;
	}

	@Override
	public void deleteById(Integer id) {
	    Optional<Producto> productoOpt = productoRepository.findById(id);

	    if (!productoOpt.isPresent()) {
	        Log log = new Log();
	        log.setLogDate(LocalDateTime.now());
	        log.setMessage("Intento de eliminar producto inexistente: ID = " + id);
	        logRepository.save(log);
	        messageProducerService.sendMessage(log);
	        return;
	    }

	    Producto producto = productoOpt.get();

	    // Eliminación lógica
	    producto.setActivo(false);
	    productoRepository.save(producto);

	    // Registrar auditoría y log
	    registrarAuditoria(producto, "ELIMINADO");
	}


    //agregado
	@Override
	public List<Producto> findBySubCategoria(Integer idSubCategoria) {
		return productoRepository.findBySubCategoria_IdSubCategoria(idSubCategoria);
	}

    //agregado
	@Override
	public List<Producto> findByLowStock(int stock) {
		 return productoRepository.findByStockLessThan(stock);
	}
	
	
	
	
	// METODO PARA REGISTRAR AUDITORIA Y LOG 
	private void registrarAuditoria(Producto producto, String accion) {
	    AuditoriaProducto auditoria = new AuditoriaProducto();
	    auditoria.setIdProducto(producto.getIdProducto());
	    auditoria.setFecha(LocalDateTime.now());
	    auditoria.setUsuarioResponsable("admin"); // IMPLEMENTAR PARA USUARIO AUTENTICADO 
	    auditoria.setEstado("Completado");

	    switch (accion) {
	        case "CREADO":
	            auditoria.setDescripcionTrabajo("Creación de producto");
	            auditoria.setDetalle("Se creó el producto '" + producto.getNombre() + "' en el inventario.");
	            break;
	        case "ACTUALIZADO":
	            auditoria.setDescripcionTrabajo("Actualización de producto");
	            auditoria.setDetalle("Se actualizó el producto '" + producto.getNombre() + "' en el inventario.");
	            break;
	        case "ELIMINADO":
	            auditoria.setDescripcionTrabajo("Eliminación de producto");
	            auditoria.setDetalle("Se eliminó el producto '" + producto.getNombre() + "' del inventario.");
	            break;
	    }

	    auditoriaProductoRepository.save(auditoria);

	    //  LOG
	    Log log = new Log();
	    log.setLogDate(LocalDateTime.now());
	    log.setMessage("Producto " + accion.toLowerCase() + ": ID = " + producto.getIdProducto() + ", Nombre = " + producto.getNombre());
	    logRepository.save(log);
	    messageProducerService.sendMessage(log);
	}



	
	
	

}
