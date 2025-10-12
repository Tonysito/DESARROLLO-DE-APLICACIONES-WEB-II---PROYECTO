package org.cibertec.controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Producto;
import org.cibertec.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> findAll() {
        return productoService.findAll();
    }
    
    //TODOS LOS PRODUCTOS ACTIVOS 
    @GetMapping("/activos")
    public List<Producto> listarActivos() {
    	 return productoService.findAllActivos();
    }


    //TODOS LOS PRODUCTOS ACTIVOS 
    @GetMapping("/inactivos")
    public List<Producto> listarInacctivos() {
    	 return productoService.findAllInactivos();
    }

    

    @GetMapping("/{id}")
    public Optional<Producto> findById(@PathVariable Integer id) {
        return productoService.findById(id);
    }
    
    @GetMapping("/subcategoria/{idSubCategoria}")
    public List<Producto> findBySubCategoria(@PathVariable Integer idSubCategoria) {
        return productoService.findBySubCategoria(idSubCategoria);
    }
    
    @PostMapping
    public Producto save(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setIdProducto(id);
        return productoService.update(producto);
    }


    // ELIMINACION LOGICA DE ESTADO BOOL :  TRUE =1     FALSE = 0 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
    	productoService.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    	productoService.deleteById(id);
    } 
    
    @GetMapping("/bajo-stock")
    public List<Producto> getProductosBajoStock() {
        return productoService.findByLowStock(5);
    }
}
