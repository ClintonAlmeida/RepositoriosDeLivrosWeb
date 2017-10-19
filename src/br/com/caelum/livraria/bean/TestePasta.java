package br.com.caelum.livraria.bean;

import java.io.File;
import java.util.Properties;

public class TestePasta {

	public static void main(String[] args) {
		
		
		File file = new File("C:\\Repositorio de Livros Web");
		
		System.out.println(file.exists());
		
		System.out.println(file.toString());
		
		Properties properties = System.getProperties();
        System.out.println(properties.toString());
        System.out.println(System.getProperty("sun.desktop"));
	}
	
}
