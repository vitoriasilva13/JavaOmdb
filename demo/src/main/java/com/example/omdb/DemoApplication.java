package com.example.omdb;

import com.example.omdb.principal.Main;
import com.example.omdb.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private SerieRepository repositorio;

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repositorio);
		main.exibeMenu();

/*		ConsumoAPI api = new ConsumoAPI();
*		String json = ""; //api.obterDados("https://coffee.alexflipnote.dev/random.json");
*		System.out.println(json);
*
*		json = api.obterDados("https://www.omdbapi.com/?t=how+i+met+your+mother&apikey=9294a4d7");
*		System.out.println(json);
*
*		ConverteDados converte = new ConverteDados();
*		Serie serie = converte.obterDados(json, Serie.class);
*		System.out.println(serie);
*
*		json = api.obterDados("https://www.omdbapi.com/?t=how+i+met+your+mother&Season=1&episode=3&apikey=9294a4d7");
*		Episodio ep = converte.obterDados(json, Episodio.class);
*		System.out.println(ep);
*
*		List<Temporada> temporadasList = new ArrayList<>();
*
*		for (int i = 1; i < serie.totalTemporadas(); i++) {
*			json = api.obterDados("https://www.omdbapi.com/?t=how+i+met+your+mother&Season=%d&apikey=9294a4d7".formatted(i));
*			Temporada temporada = converte.obterDados(json, Temporada.class);
*			temporadasList.add(temporada);
*		}
*		temporadasList.forEach(System.out::println);
*/
	}
}
