package br.com.caelum.livraria.modelo;

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comentario {

	@Id
	@GeneratedValue
	private Integer id;

	private String mensagem;

	private String autorDoComentario;

	@Temporal(TemporalType.DATE)
	private Calendar dataLancamento = Calendar.getInstance();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getAutorDoComentario() {
		return autorDoComentario;
	}

	public void setAutorDoComentario(String autorDoComentario) {
		this.autorDoComentario = autorDoComentario;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	@Override
	public String toString() {
	
		return this.mensagem;
	}

}
