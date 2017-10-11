package br.com.caelum.livraria.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private String titulo;
	private String isbn;
	@Column(columnDefinition = "VARCHAR(1000)")
	private String sinopse;
	@Temporal(TemporalType.DATE)
	private Calendar dataLancamento = Calendar.getInstance();
	private Integer avaliacao;

	public Integer getAvaliacao() {

		return avaliacao;
	}

	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	@OneToOne(mappedBy="livro")
	private Arquivo arquivo;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autor> autores = new ArrayList<Autor>();

	@OneToMany(fetch=FetchType.EAGER, mappedBy="livro")
	@Fetch(FetchMode.SUBSELECT)
	
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	
	public void adicionaComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}
	
	
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	
	

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void adicionaAutor(Autor autor) {
		this.autores.add(autor);
	}
	
	

	public Arquivo getArquivo() {
		return arquivo;
	}
	
	

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Livro() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public void removeAutor(Autor autor) {
		this.autores.remove(autor);
	}

}