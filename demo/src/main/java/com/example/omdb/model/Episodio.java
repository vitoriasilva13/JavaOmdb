package com.example.omdb.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio() {}

    public Episodio(Integer numeroTemporada, EpisodioRecord episodioRecord) {
        this.temporada = numeroTemporada;
        this.titulo = episodioRecord.titulo();
        this.numeroEpisodio = episodioRecord.numeroEpisodio();

        try {
            this.avaliacao = Double.valueOf(episodioRecord.avaliacao());
            this.dataLancamento = LocalDate.parse(episodioRecord.dataLancamento());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        } catch (DateTimeParseException e) {
            this.dataLancamento = null;
        }

    }

    public Episodio(Integer temporada, String titulo, Integer numeroEpisodio, Double avaliacao, LocalDate dataLancamento) {
        this.temporada = temporada;
        this.titulo = titulo;
        this.numeroEpisodio = numeroEpisodio;
        this.avaliacao = avaliacao;
        this.dataLancamento = dataLancamento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "[" +
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao='" + avaliacao + '\'' +
                ", dataLancamento=" + dataLancamento +
                ']';
    }
}
