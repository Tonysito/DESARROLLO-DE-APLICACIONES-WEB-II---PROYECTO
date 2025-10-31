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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private AuditoriaProductoRepository auditoriaProductoRepository;

    // --------------------- PROTECTED METHODS ---------------------

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackFindAll")
    @Retry(name = "productoService")
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackFindAll")
    @Retry(name = "productoService")
    public List<Producto> findAllActivos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackFindAll")
    @Retry(name = "productoService")
    public List<Producto> findAllInactivos() {
        return productoRepository.findByActivoFalse();
    }

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackFindById")
    @Retry(name = "productoService")
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackSaveOrUpdate")
    @Retry(name = "productoService")
    public Producto save(Producto producto) {
        Producto nuevoProducto = productoRepository.save(producto);
        registrarAuditoria(nuevoProducto, "CREADO");
        return nuevoProducto;
    }

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackSaveOrUpdate")
    @Retry(name = "adminCB")
    public Producto update(Producto producto) {
        Producto productoActualizado = productoRepository.save(producto);
        registrarAuditoria(productoActualizado, "ACTUALIZADO");
        return productoActualizado;
    }

    @Override
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackDeleteById")
    @Retry(name = "adminCB")
    public void deleteById(Integer id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);

        if (!productoOpt.isPresent()) {
            Log log = new Log();
            log.setLogDate(LocalDateTime.now());
            log.setMessage("Intento de eliminar producto inexistente: ID = " + id);
            sendLogSafely(log);
            return;
        }

        Producto producto = productoOpt.get();
        producto.setActivo(false);
        productoRepository.save(producto);
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
	

    // --------------------- FALLBACK METHODS ---------------------

    public List<Producto> fallbackFindAll(Throwable ex) {
        System.err.println("Fallo al obtener lista de productos. Fallback activado: " + ex.getMessage());
        return List.of();
    }

    public Optional<Producto> fallbackFindById(Integer id, Throwable ex) {
        System.err.println("Fallo al buscar producto ID=" + id + ". Fallback activado: " + ex.getMessage());
        return Optional.empty();
    }

    public Producto fallbackSaveOrUpdate(Producto producto, Throwable ex) {
        System.err.println("Fallo al guardar/actualizar producto. Fallback activado: " + ex.getMessage());
        return producto;
    }

    public void fallbackDeleteById(Integer id, Throwable ex) {
        System.err.println("Fallo al eliminar producto ID=" + id + ". Fallback activado: " + ex.getMessage());
    }

    
    
    // --------------------- LOGS Y AUDITORIA ---------------------

    private void registrarAuditoria(Producto producto, String accion) {
        AuditoriaProducto auditoria = new AuditoriaProducto();
        auditoria.setIdProducto(producto.getIdProducto());
        auditoria.setFecha(LocalDateTime.now());
        auditoria.setUsuarioResponsable("admin");
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
        registraryEnviarLog(accion, producto);
    }

    private void registraryEnviarLog(String accion, Producto producto) {
        Log log = new Log();
        log.setLogDate(LocalDateTime.now());
        log.setMessage("Producto " + accion.toLowerCase() +
                       ": ID = " + producto.getIdProducto() +
                       ", Nombre = " + producto.getNombre());
        sendLogSafely(log);
    }

    
    
    
    
    @CircuitBreaker(name = "adminCB", fallbackMethod = "fallbackSendLog")
    @Retry(name = "adminCB")
    public void sendLogSafely(Log log) {
        logRepository.save(log);
        messageProducerService.sendMessage(log);
    }

    public void fallbackSendLog(Log log, Throwable ex) {
        System.err.println("No se pudo enviar log: " + log.getMessage() + " - Causa: " + ex.getMessage());
    }


	
}
