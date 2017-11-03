package br.com.caelum.livraria.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Arquivo;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class DescarregadorBean {

	private StreamedContent streamedContent;
	private Arquivo arquivo = new Arquivo();

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public void descarregar(Livro livro) throws IOException {

		LivroBean livroBean = new LivroBean();
		
		if(livro.getQtdDownload() == null) {
			int temp = 1;
			livro.setQtdDownload(temp);
		}else {
			livro.setQtdDownload(livro.getQtdDownload() + 1);
		}
		
		DAO<Livro> livroDao = new DAO<Livro>(Livro.class);
		
		livroDao.atualiza(livro);
		
		arquivo.setId(livro.getArquivo().getId());
		Arquivo arquivo2 = new DAO<Arquivo>(Arquivo.class).buscaPorId(arquivo.getId());

		System.out.println(arquivo2.getNomeArquivo());
		System.out.println(arquivo2.getCaminhoArquivo());

		// Abaixo temos um código estático, mas
		// obviamente você pode buscar o arquivo de onde quiser :)
		InputStream in = new FileInputStream(new File(livroBean.diretorioRaiz()+ "\\" + arquivo2.getNomeArquivo()));
		streamedContent = new DefaultStreamedContent(in, "image/PNG", arquivo2.getNomeArquivo());
	}

}