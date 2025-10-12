package org.cibertec.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_Usuario")
	    private Integer idUsuario;

	    @Column(nullable = false, length = 100)
	    private String nombre;

	    @Column(nullable = false, length = 100)
	    private String apellido;

	    @Column(nullable = false, unique = true, length = 150)
	    private String email;

	    @Column(nullable = false, length = 255)
	    private String password;

	    @Enumerated(EnumType.STRING)
	    @Column(columnDefinition = "ENUM('ADMIN') DEFAULT 'ADMIN'")
	    private Rol rol = Rol.ADMIN;

	    @Column(name = "fecha_creacion", updatable = false, insertable = false,
	            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private LocalDateTime fechaCreacion;

	    // Enum interno para el rol
	    public enum Rol {
	        ADMIN
	    }

	    // Getters y Setters
	    public Integer getIdUsuario() {
	        return idUsuario;
	    }

	    public void setIdUsuario(Integer idUsuario) {
	        this.idUsuario = idUsuario;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getApellido() {
	        return apellido;
	    }

	    public void setApellido(String apellido) {
	        this.apellido = apellido;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public Rol getRol() {
	        return rol;
	    }

	    public void setRol(Rol rol) {
	        this.rol = rol;
	    }

	    public LocalDateTime getFechaCreacion() {
	        return fechaCreacion;
	    }

	    public void setFechaCreacion(LocalDateTime fechaCreacion) {
	        this.fechaCreacion = fechaCreacion;
	    }
}
