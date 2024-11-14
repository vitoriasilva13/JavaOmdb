package com.example.omdb;

import com.example.omdb.principal.Main;
import com.example.omdb.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeriesAppComWeb {
	public static void main(String[] args) {
		SpringApplication.run(SeriesAppComWeb.class, args);
	}
}
