package com.example.omdb.controller;

import com.example.omdb.dto.EpisodioDTO;
import com.example.omdb.dto.SerieDTO;
import com.example.omdb.servicos.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieControler {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> getSeries() {
        return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5Series() {
        return service.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getSeriesLancamentos() {
        return service.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO getSerieById(@PathVariable Long id) {
        return service.obterSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return service.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{temporada}")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id, @PathVariable Long temporada) {
        return service.obterTemporada(id, temporada);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterPorCategoria(@PathVariable String categoria) {
        return service.obterPorCategoria(categoria);
    }
}
