package com.example.omdb.dto;

import com.example.omdb.model.Categoria;

public record SerieDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Categoria genero,
                       String atores,
                       String posterUrl,
                       String sinopse) {

}
