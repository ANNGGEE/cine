package com.example.cine;

import com.example.cine.services.*;
import com.example.cine.util.CineConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CineApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(CineApplication.class, args);

		// Ejecutar la consola
		CineConsole console = new CineConsole(
				context.getBean(AsistenteService.class),
				context.getBean(SalaService.class),
				context.getBean(PeliculaService.class),
				context.getBean(ProyeccionService.class),
				context.getBean(EntradaService.class)
		);

		console.run();
	}
}
