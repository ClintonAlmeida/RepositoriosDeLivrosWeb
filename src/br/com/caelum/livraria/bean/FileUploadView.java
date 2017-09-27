package br.com.caelum.livraria.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Arquivo;

@ManagedBean
@SessionScoped
public class FileUploadView {

	private UploadedFile uploadedFile;
	private Arquivo arquivo = new Arquivo();

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void upload() {
		try {
			File file = new File(diretorioRaiz(), uploadedFile.getFileName());

			DAO<Arquivo> dao = new DAO<Arquivo>(Arquivo.class);
			arquivo.setNomeArquivo(uploadedFile.getFileName());
			arquivo.setCaminhoArquivo(diretorioRaiz());
			arquivo.setTamanhoArquivo(uploadedFile.getSize());
			
			dao.adiciona(arquivo);
			
			
			OutputStream out = new FileOutputStream(file);
			out.write(uploadedFile.getContents());
			out.close();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Upload completo", "O arquivo " + uploadedFile.getFileName() + " foi salvo!"));
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		}

	}

	private String diretorioRaiz() {

		return "C:\\Ha";
	}

}