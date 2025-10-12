package org.cibertec.entity.auditoria;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos_bajo_stock")
public class ProductoBajoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idreporte;

    @Column(name = "fechareporte")
    private LocalDateTime fechareporte;

    @Column(name = "idproducto")
    private Integer idproducto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precio")
    private BigDecimal precio;


    // Getters y Setters
    public Integer getIdreporte() {
        return idreporte;
    }

    public void setIdreporte(Integer idreporte) {
        this.idreporte = idreporte;
    }

    public LocalDateTime getFechareporte() {
        return fechareporte;
    }

    public void setFechareporte(LocalDateTime fechareporte) {
        this.fechareporte = fechareporte;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }


}