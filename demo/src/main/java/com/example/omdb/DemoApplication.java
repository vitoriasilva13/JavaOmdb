package com.example.omdb;

import com.example.omdb.servicos.ConsumoAPI;
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
		String json = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=9294a4d7");
		System.out.println(json);
		json = api.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);


	}
}
