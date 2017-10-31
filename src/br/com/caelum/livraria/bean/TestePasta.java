package br.com.caelum.livraria.bean;

import java.io.File;




import java.io.IOException;
public class TestePasta {

	
	
	
	public static void main(String[] args) throws IOException {
		
		
		LivroBean livro = new LivroBean();
		
		
		System.out.println(livro.diretorioRaiz());
		
		
		String nomeAndCaminhoDaPasta; 
		
		
		//try {
		//	System.out.println("/  -> " + new File("/").getCanonicalPath());
		//	System.out.println(".. -> " + new File("..").getCanonicalPath());
		//	System.out.println(".  -> " + new File(".").getCanonicalPath());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
}
