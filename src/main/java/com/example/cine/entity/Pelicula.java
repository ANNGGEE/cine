package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPelicula;

    private String titulo;
    private Integer duracion;
    private String genero;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Proyeccion> proyecciones;

//}
//    public Long getIdPelicula() {
//        return idPelicula;
//    }
//
//    public void setIdPelicula(Long idPelicula) {
//        this.idPelicula = idPelicula;
//    }
//
//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }
//
//    public Integer getDuracion() {
//        return duracion;
//    }
//
//    public void setDuracion(Integer duracion) {
//        this.duracion = duracion;
//    }
//
//    public String getGenero() {
//        return genero;
//    }
//
//    public void setGenero(String genero) {
//        this.genero = genero;
//    }
//
//    public List<Proyeccion> getProyecciones() {
//        return proyecciones;
//    }
//
//    public void setProyecciones(List<Proyeccion> proyecciones) {
//        this.proyecciones = proyecciones;
//    }
}
