package com.example.cine.entity;

import ch.qos.logback.core.pattern.util.AlmostAsIsEscapeUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Proyeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyeccion;

    private LocalTime horario;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "idSala", nullable = false)
    @JsonIgnoreProperties({"proyecciones", "butacas"}) // evita bucles con Sala
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "idPelicula", nullable = false)
    @JsonIgnoreProperties("proyecciones") // evita bucle con Pelicula
    private Pelicula pelicula;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("proyeccion") // evita bucle con Entrada
    private List<Entrada> entradas;

    @ManyToMany(mappedBy = "proyecciones")
    @JsonIgnoreProperties({"entradas", "proyecciones"})
    private List<Asistente> asistentes;
}

//    public Long getIdProyeccion() {
//        return idProyeccion;
//    }
//
//    public void setIdProyeccion(Long idProyeccion) {
//        this.idProyeccion = idProyeccion;
//    }
//
//    public LocalTime getHorario() {
//        return horario;
//    }
//
//    public void setHorario(LocalTime horario) {
//        this.horario = horario;
//    }
//
//    public Pelicula getPelicula() {
//        return pelicula;
//    }
//
//    public void setPelicula(Pelicula pelicula) {
//        this.pelicula = pelicula;
//    }
//
//    public List<Entrada> getEntradas() {
//        return entradas;
//    }
//
//    public void setEntradas(List<Entrada> entradas) {
//        this.entradas = entradas;
//    }
//
//    public List<Asistente> getAsistentes() {
//        return asistentes;
//    }
//
//    public void setAsistentes(List<Asistente> asistentes) {
//        this.asistentes = asistentes;
//    }
//
//    public LocalDate getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(LocalDate fecha) {
//        this.fecha = fecha;
//    }
//
//    public Sala getSala() {
//        return sala;
//    }
//
//    public void setSala(Sala sala) {
//        this.sala = sala;
//    }
//}
