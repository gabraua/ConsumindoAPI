package br.com.estudando.screemmatch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.estudando.screemmatch.models.DadosEpisodio;
import br.com.estudando.screemmatch.models.DadosSerie;
import br.com.estudando.screemmatch.models.DadosTemporada;
import br.com.estudando.screemmatch.models.Episodio;
import br.com.estudando.screemmatch.service.ConsumoApi;
import br.com.estudando.screemmatch.service.ConverteDados;

public class Principal {

	private Scanner leitura = new Scanner(System.in);
	private ConsumoApi consumo = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();

	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=6585022c";

	public void exibeMenu() {
		System.out.println("Digite o nome da série para busca");
		var nomeSerie = leitura.nextLine();
		var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			var json3 = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json3, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		List<DadosEpisodio> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream())
				.collect(Collectors.toList());
		dadosEpisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
				.sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()).limit(10)
				.forEach(System.out::println);
		

		List<Episodio> episodios = temporadas.stream()
			    .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(), d)))
			    .collect(Collectors.toList());
			episodios.forEach(System.out::println);


//			System.out.println("Digite o título do episódio:");
//			var TrechoTitulo = leitura.nextLine();
//			Optional<Episodio> episodioBuscado = episodios.stream()
//				.filter(e -> e.getTitulo().toUpperCase().contains(TrechoTitulo.toUpperCase()))
//				.findFirst();
//			if(episodioBuscado.isPresent()) {
//				System.out.println("Episódio encontrado");
//				System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//			}else {
//				System.out.println("NÃO ECONTRADO");
//			}

			
		Map<Integer, Double> avaliacaoTemporada = episodios.stream()
				.filter(e -> e.getAvaliacao() > 0.0)
				.collect(Collectors.groupingBy(Episodio::getTemporada,Collectors.averagingDouble(Episodio::getAvaliacao)));
		System.out.println(avaliacaoTemporada);


		

//		System.out.println("A partir de que ano vc deseja assistir?");
//		var ano = leitura.nextInt();
//		leitura.nextLine();
//		
//		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//		episodios.stream()
//			.filter(e ->e.getLocalDate()!= null && e.getLocalDate().isAfter(dataBusca))
//			.forEach(e -> System.out.println(
//			        "Temporada: " + e.getTemporada() + 
//			        " Data lancamento: " + e.getLocalDate().format(formatador) +
//			        " Episodios: " + e.getTitulo()
//			));
		
		DoubleSummaryStatistics est = episodios.stream()
			.filter(e -> e.getAvaliacao() > 0.0)
			.collect(Collectors.summarizingDouble(Episodio:: getAvaliacao));
		System.out.println(est);
		
	}

}
