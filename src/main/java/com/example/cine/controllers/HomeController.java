package com.example.cine.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> menuPrincipal() {
        return Map.of(
                "Asistentes", "/asistentes",
                "Proyecciones", "/proyecciones",
                "Entradas", "/entradas",
                "Comprar entrada", "/entradas/comprar",
                "Asientos libres", "/entradas/libres",
                "Cancelar entrada", "/entradas/cancelar/{id}",
                "Ocupación proyección", "/entradas/ocupacion/{id}"
        );
    }
}
