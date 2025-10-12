package org.cibertec.entity.auditoria;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "auditoria_producto")
public class AuditoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMantenimiento;

    @Column(nullable = false)
    private Integer idProducto;

    @Column(length = 200, nullable = false)
    private String descripcionTrabajo; // Ej: "Reducción de stock"

    private LocalDateTime fecha;

    @Column(length = 100)
    private String usuarioResponsable;

    @Column(length = 500)
    private String detalle = "-"; // Por defecto "-"

    @Column(length = 20)
    private String estado = "Pendiente"; // Por defecto "Pendiente"

    // ===== Getters & Setters =====

    public Integer getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(Integer idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

  public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}