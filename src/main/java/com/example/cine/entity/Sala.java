package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Data
@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sala;

    private String descripcion;
    private int numButaca;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Proyeccion> proyecciones;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Butaca> butacas;
}
