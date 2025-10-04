package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Producto;
import org.cibertec.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<Producto> findAll() {
		 return productoRepository.findAll();
	}

	@Override
	public Optional<Producto> findById(Integer id) {
		  return productoRepository.findById(id);
	}

	@Override
	public Producto save(Producto product) {
		  return productoRepository.save(product);
	}

	@Override
	public Producto update(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public void deleteById(Integer id) {
		productoRepository.deleteById(id);
		
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

}
