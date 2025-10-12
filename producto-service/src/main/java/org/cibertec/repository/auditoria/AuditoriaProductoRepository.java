package org.cibertec.repository.auditoria;


import org.cibertec.entity.auditoria.AuditoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaProductoRepository extends JpaRepository<AuditoriaProducto, Integer> {
}