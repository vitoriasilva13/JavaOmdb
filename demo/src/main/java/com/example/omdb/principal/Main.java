package com.example.omdb.principal;

import com.example.omdb.model.Episodio;
import com.example.omdb.model.EpisodioRecord;
import com.example.omdb.model.SerieRecord;
import com.example.omdb.model.TemporadaRecord;
import com.example.omdb.servicos.ConsumoAPI;
import com.example.omdb.servicos.ConverteDados;
import com.example.omdb.util.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.omdb.util.Util.formataSoutTitulo;

public class Main {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey={yourapikey}";

    private Scanner s = new Scanner(System.in);
    private ConsumoAPI api = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Informe o nome da série:");
        String serieInput = s.nextLine();

        String json = api.obterDados(ENDERECO + serieInput.replace(" ", "+") + API_KEY);

        SerieRecord serie = conversor.obterDados(json, SerieRecord.class);
        System.out.println(serie);

        System.out.println(formataSoutTitulo("Mostra temporadas e lista de episódios"));

        List<TemporadaRecord> temporadasList = new ArrayList<>();

        for (int i = 1; i < serie.totalTemporadas(); i++) {
            json = api.obterDados(ENDERECO + serieInput.replace(" ", "+") + "&season=" + i + API_KEY);
            TemporadaRecord temporada = conversor.obterDados(json, TemporadaRecord.class);
            temporadasList.add(temporada);
        }
        temporadasList.forEach(System.out::println);

        temporadasList.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//        for (int i = 0; i < serie.totalTemporadas(); i++) {
//            List<EpisodioRecord> episodiosList = temporadasList.get(i).episodios();
//            for (int j = 0; j < episodiosList.size(); j++) {
//                System.out.println(episodiosList.get(j).titulo());
//            }
//        }

        System.out.println(formataSoutTitulo("Mostra top 5 episódios com melhor avaliação"));

        List<EpisodioRecord> episodioList = temporadasList.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList()); // apenas .toList() é imutável e não é possível .add

        System.out.println("Top 5 episódios com melhor avaliação:");

        episodioList.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodioRecord::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        System.out.println(formataSoutTitulo("Lista episódios com data de lançamento"));
        List<Episodio> episodios = temporadasList.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.numero(), e)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println(formataSoutTitulo("Busca de episódios por ano"));
        System.out.println("Digite um ano para busca de episódios da série a partir dele:");
        int ano = s.nextInt();
        s.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() +
                        "\nEpisódio: " + e.getNumeroEpisodio() + " - " + e.getTitulo() +
                        "\nData Lançamento:  " + e.getDataLancamento().format(formatter)));

        System.out.println(formataSoutTitulo("Busca de episódio por título"));
        System.out.println("Digite o título de um episódio para buscar sua temporada:");
        String titulo = s.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();

//        if (episodioBuscado.isPresent())
//            System.out.println("Episódio encontrado! Temporada:  " + episodioBuscado.get().getTemporada());
//        else
//            System.out.println("Episódio não encontrado");

        episodioBuscado.ifPresentOrElse(e -> System.out.println("Episódio encontrado! Temporada:  " + e.getTemporada()), () -> System.out.println("Episódio não encontrado"));

        System.out.println(formataSoutTitulo("Média de avaliação por temporada"));
        Map<Integer,Double> avaliacoesMap = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesMap);

        System.out.println(formataSoutTitulo("Estatísticas de avaliações da série"));
        DoubleSummaryStatistics estatisticas = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println(estatisticas);
        System.out.println("Média: " + estatisticas.getAverage());
        System.out.println("Maior nota: " + estatisticas.getMax());
        System.out.println("Menor nota: " + estatisticas.getMin());
        System.out.println("Quantidade: " + estatisticas.getCount());
    }
}
