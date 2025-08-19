package com.aluracursos.literatura;

import com.aluracursos.literatura.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	private final Main main;
	public LiteraturaApplication(Main m) { this.main = m; }
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		main.showMenu();
	}
}
