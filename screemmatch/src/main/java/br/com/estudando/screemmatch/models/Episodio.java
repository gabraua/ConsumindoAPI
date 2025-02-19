package br.com.estudando.screemmatch.models;

import java.time.LocalDate;

public class Episodio {

	private Integer temporada;
	private String titulo;
	private Integer numeroEpisodio;
	private Double avaliacao;
	private LocalDate localDate;

	public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
		this.temporada = numeroTemporada;
		this.titulo = dadosEpisodio.titulo();
		this.numeroEpisodio = dadosEpisodio.numero();
		try {
			this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
			this.localDate = LocalDate.parse(dadosEpisodio.datalancamento());
		} catch (NumberFormatException ex) {
			this.avaliacao=0.0;
		}
		

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

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	@Override
	public String toString() {
		return "temporada=" + temporada + ", titulo=" + titulo + ", numeroEpisodio=" + numeroEpisodio
				+ ", avaliacao=" + avaliacao + ", localDate=" + localDate;
	}
	
	

}
