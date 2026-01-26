package com.example.cine.controllers;

import com.example.cine.entity.Entrada;
import com.example.cine.services.EntradaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entradas")
public class EntradaController{
    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @PostMapping("/comprar")
    public ResponseEntity<Entrada> comprarEntrada(
            @RequestParam Long id_asistente,
            @RequestParam Long id_proyeccion,
            @RequestParam Long id_butaca,
            @RequestParam Double precio
    ){
        Entrada entrada = entradaService.comprarEntrada(id_asistente, id_proyeccion, id_butaca, precio);
            return ResponseEntity.ok(entrada);
    }
}
