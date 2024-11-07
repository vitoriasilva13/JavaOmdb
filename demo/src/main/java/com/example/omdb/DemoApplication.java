package com.example.omdb;

import com.example.omdb.model.Serie;
import com.example.omdb.servicos.ConsumoAPI;
import com.example.omdb.servicos.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI api = new ConsumoAPI();
		String json = api.obterDados("https://www.omdbapi.com/?t=how+i+met+your+mother&Season=1&apikey=9294a4d7");
		System.out.println(json);

		ConverteDados converteDados = new ConverteDados();
		Serie serie = converteDados.obterDados(json, Serie.class);
		System.out.println(serie);

		json = api.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
	}
}
