package br.com.caelum.livraria.exception;

import java.io.IOException;

import org.primefaces.model.UploadedFile;

public class VerificaPdf extends IOException {

	private UploadedFile uploadedFile;
	
	public VerificaPdf(UploadedFile uploadedFile) {
		
		this.uploadedFile = uploadedFile;
		
	}

}
