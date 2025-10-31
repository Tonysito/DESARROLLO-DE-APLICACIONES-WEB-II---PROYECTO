package org.cibertec.repository;

import java.util.Optional;

import org.cibertec.entity.auditoria.ProductoBajoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlStockRepository extends JpaRepository<ProductoBajoStock, Integer> {

    Optional<ProductoBajoStock> findByIdproducto(Integer idProducto);

}