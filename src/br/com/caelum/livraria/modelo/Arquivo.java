package br.com.caelum.livraria.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Arquivo {

	@Id
	@GeneratedValue
	private Integer id;

	private String nomeArquivo;

	private String caminhoArquivo;

	private long tamanhoArquivo;

	@OneToOne
	private Livro livro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
