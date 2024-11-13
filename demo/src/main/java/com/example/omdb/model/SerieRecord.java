package com.example.omdb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe que representa uma série
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieRecord(@JsonAlias({"Title", "Titulo"}) String titulo,
                          @JsonAlias("totalSeasons") Integer totalTemporadas,
                          @JsonAlias("imdbRating") String avaliacao,
                          @JsonAlias("Genre") String genero,
                          @JsonAlias("Actors") String atores,
                          @JsonAlias("Poster") String posterUrl,
                          @JsonAlias("Plot") String sinopse) { }
