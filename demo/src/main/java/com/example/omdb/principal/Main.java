package com.example.omdb.principal;

import com.example.omdb.model.*;
import com.example.omdb.repository.SerieRepository;
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
    private final String API_KEY = "&apikey=" + System.getenv("OMBD_API_KEY");

    private List<SerieRecord> serieRecordList = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBusca;

    private Scanner s = new Scanner(System.in);
    private ConsumoAPI api = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private SerieRepository repositorio;

    public Main(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar série por ator
                    6 - Buscar top 5 séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries por temporada e avaliação
                    9 - Buscar episódio por trecho do nome
                    10 - Buscar top episódios por série
                    11 - Buscar episodios a partir de uma data
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = s.nextInt();
            s.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    buscarTopEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosAPartirDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

//        System.out.println("Informe o nome da série:");
//        String serieInput = s.nextLine();
//
//        String json = api.obterDados(ENDERECO + serieInput.replace(" ", "+") + API_KEY);
//
//        SerieRecord serie = conversor.obterDados(json, SerieRecord.class);
//        System.out.println(serie);
//
//        System.out.println(formataSoutTitulo("Mostra temporadas e lista de episódios"));
//
//        List<TemporadaRecord> temporadasList = new ArrayList<>();
//
//        for (int i = 1; i < serie.totalTemporadas(); i++) {
//            json = api.obterDados(ENDERECO + serieInput.replace(" ", "+") + "&season=" + i + API_KEY);
//            TemporadaRecord temporada = conversor.obterDados(json, TemporadaRecord.class);
//            temporadasList.add(temporada);
//        }
//        temporadasList.forEach(System.out::println);
//
//        temporadasList.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
////        for (int i = 0; i < serie.totalTemporadas(); i++) {
////            List<EpisodioRecord> episodiosList = temporadasList.get(i).episodios();
////            for (int j = 0; j < episodiosList.size(); j++) {
////                System.out.println(episodiosList.get(j).titulo());
////            }
////        }
//
//        System.out.println(formataSoutTitulo("Mostra top 5 episódios com melhor avaliação"));
//
//        List<EpisodioRecord> episodioList = temporadasList.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList()); // apenas .toList() é imutável e não é possível .add
//
//        System.out.println("Top 5 episódios com melhor avaliação:");
//
//        episodioList.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodioRecord::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);
//
//        System.out.println(formataSoutTitulo("Lista episódios com data de lançamento"));
//        List<Episodio> episodios = temporadasList.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(e -> new Episodio(t.numero(), e)))
//                .collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);
//
//        System.out.println(formataSoutTitulo("Busca de episódios por ano"));
//        System.out.println("Digite um ano para busca de episódios da série a partir dele:");
//        int ano = s.nextInt();
//        s.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() +
//                        "\nEpisódio: " + e.getNumeroEpisodio() + " - " + e.getTitulo() +
//                        "\nData Lançamento:  " + e.getDataLancamento().format(formatter)));
//
//        System.out.println(formataSoutTitulo("Busca de episódio por título"));
//        System.out.println("Digite o título de um episódio para buscar sua temporada:");
//        String titulo = s.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(titulo.toUpperCase()))
//                .findFirst();
//
////        if (episodioBuscado.isPresent())
////            System.out.println("Episódio encontrado! Temporada:  " + episodioBuscado.get().getTemporada());
////        else
////            System.out.println("Episódio não encontrado");
//
//        episodioBuscado.ifPresentOrElse(e -> System.out.println("Episódio encontrado! Temporada:  " + e.getTemporada()), () -> System.out.println("Episódio não encontrado"));
//
//        System.out.println(formataSoutTitulo("Média de avaliação por temporada"));
//        Map<Integer,Double> avaliacoesMap = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                Collectors.averagingDouble(Episodio::getAvaliacao)));
//        System.out.println(avaliacoesMap);
//
//        System.out.println(formataSoutTitulo("Estatísticas de avaliações da série"));
//        DoubleSummaryStatistics estatisticas = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.println(estatisticas);
//        System.out.println("Média: " + estatisticas.getAverage());
//        System.out.println("Maior nota: " + estatisticas.getMax());
//        System.out.println("Menor nota: " + estatisticas.getMin());
//        System.out.println("Quantidade: " + estatisticas.getCount());
    }

    private void buscarSerieWeb() {
        SerieRecord dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repositorio.save(serie);
        //serieRecordList.add(dados);
        System.out.println(dados);
    }

    private SerieRecord getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = s.nextLine();
        var json = api.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        SerieRecord dados = conversor.obterDados(json, SerieRecord.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        String nome = s.nextLine();

        //SerieRecord dadosSerie = getDadosSerie();
        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nome);
//                series.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nome.toLowerCase()))
//                .findFirst();

        if(serie.isPresent()) {
            Serie serieEncontrada = serie.get();
            List<TemporadaRecord> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = api.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                TemporadaRecord dadosTemporada = conversor.obterDados(json, TemporadaRecord.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);

        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void listarSeriesBuscadas() {
//        List<Serie> series = serieRecordList.stream()
//                                .map(s -> new Serie(s))
//                                .collect(Collectors.toList());

        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome:");
        String nome = s.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nome);

        if (serieBusca.isPresent()) {
            System.out.println(serieBusca.get());
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Serie por ator:");
        String nomeAtor = s.nextLine();
        System.out.println("Com avaliações a partir de:");
        Double avaliacao = s.nextDouble();
        //List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCase(nomeAtor);
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que o ator " + nomeAtor + " atuou:");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> top5Series = repositorio.findTop5ByOrderByAvaliacaoDesc();
        System.out.println("Top 5 séries:");
        top5Series.forEach(s -> System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Gênero desejado para lista filmes:");
        String nomeGenero = s.nextLine();
        Categoria categoria = Categoria.fromStringPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Séries do gênero " + categoria + ":");
        seriesPorCategoria.forEach(s -> System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = s.nextInt();
        s.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = s.nextDouble();
        s.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaEAvaliacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Trecho do nome do episódio:");
        String nomeEpisodio = s.nextLine();

        List<Episodio> episodios = repositorio.episodiosPorTrecho(nomeEpisodio);
        episodios.forEach(e ->
                System.out.printf("Série: %s - Temporada %d - Episódio %d - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void buscarTopEpisodiosPorSerie() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s - Temporada %d - Episódio %d - %s %.2f\n",
                            e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodiosAPartirDeUmaData(){
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano limite do lançamento:");
            Integer ano = s.nextInt();
            s.nextLine();
            List<Episodio> episodiosAno = repositorio.episodiosPorSerieAno(serie, ano);
            episodiosAno.forEach(System.out::println);
        }
    }
}
