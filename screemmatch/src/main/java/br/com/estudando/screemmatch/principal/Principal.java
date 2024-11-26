package br.com.estudando.screemmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.estudando.screemmatch.models.DadosEpisodio;
import br.com.estudando.screemmatch.models.DadosSerie;
import br.com.estudando.screemmatch.models.DadosTemporada;
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
		
		for (int i = 1; i<=dados.totalTemporadas(); i++) {
			var json3 = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY );
			DadosTemporada dadosTemporada = conversor.obterDados(json3, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
		temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
		
//		for(int i = 0; i<dados.totalTemporadas();i++) {
//			List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//			for(int j=0;j< episodiosTemporada.size();j++) {
//				System.out.println(episodiosTemporada.get(j).titulo());
//			}
//		}
		
		
		
	}
	
	
}
