package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Data
@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSala;

    private int numero;
    private String descripcion;
    private int capacidad;
    private int numButaca;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Proyeccion> proyecciones;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Butaca> butacas;
}
// @Query("SELECT s FROM Sala s JOIN FETCH s.proyecciones WHERE s.id = :id")
//Sala findByIdWithProyecciones(@Param("id") Long id);
