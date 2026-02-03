package com.example.cine;

import com.example.cine.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineApplication.class, args);
	}
}
// Falla al comprar una butaca ya comprada otra vez
// Falla a la hora de ver las entradas de un asistente
// Me da error al listar las entradas de un asistente si entro y vuelvo a salir
// Deja comprar más de 5 entradas
// No debe dejar cancelar entradas dos horas antes de la proyección
// Poner un precio fijo, que no deje que lo ponga el usuario
