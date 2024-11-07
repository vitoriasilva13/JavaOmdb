package com.example.omdb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemporadaRecord(@JsonAlias("Season") Integer numero,
                              @JsonAlias("Episodes") List<EpisodioRecord> episodios) { }
