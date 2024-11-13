package com.example.omdb.model;

import com.example.omdb.servicos.ConsultaChatGPT;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Classe que representa uma série
 */
@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String posterUrl;
    private String sinopse;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() { }

    /**
     * Construtor da classe Serie
     * @param serieRecord
     */
    public Serie(SerieRecord serieRecord) {
        this.titulo = serieRecord.titulo();
        this.totalTemporadas = serieRecord.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(serieRecord.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(serieRecord.genero().split(",")[0].trim());
        this.atores = serieRecord.atores();
        this.posterUrl = serieRecord.posterUrl();
        this.sinopse = ConsultaChatGPT.obterTraducao(serieRecord.sinopse()).trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    /**
     * Método que retorna uma representação textual da série
     * @return String
     */
    @Override
    public String toString() {
        return """
          --
          Gênero: %s
          Título: '%s'
          Total de Temporadas: %d
          Avaliação: %.2f
          Atores: '%s'
          Poster: '%s'
          Sinopse: '%s'
          Episódios: %s
        """.formatted(genero, titulo, totalTemporadas, avaliacao, atores, posterUrl, sinopse, episodios);
    }
}
