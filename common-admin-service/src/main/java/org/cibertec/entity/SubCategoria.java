package org.cibertec.entity;

import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Sub_Categorias")
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Sub_Categoria")
    private Integer idSubCategoria;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Relación con Categoria
    @ManyToOne
    @JoinColumn(name = "id_Categoria", nullable = false)
    @JsonBackReference // Evita recursión hacia Categoria
    private Categoria categoria;

    // Relación con Productos
    @OneToMany(mappedBy = "subCategoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

    // Getters y Setters
    public Integer getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Integer idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
