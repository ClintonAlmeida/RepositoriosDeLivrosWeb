package br.com.caelum.livraria.bean;

import javax.persistence.EntityManager;

import br.com.caelum.livraria.dao.JPAUtil;
import br.com.caelum.livraria.modelo.Arquivo;
import br.com.caelum.livraria.modelo.Livro;

public class TesteRelacionamento {

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		Livro livro = new Livro();
		Arquivo arquivo = new Arquivo();
		
		//em.detach(livro);
		//em.detach(arquivo);
		
		
		arquivo.setId(1);
		livro.setId(1);
		
		livro = em.find(Livro.class, 1);
		
		
		
		
		if(arquivo.getId() != null) {
			
			System.out.println("Nome do arquivo: " + livro.getArquivo().getNomeArquivo());
		}else {
			
			System.out.println("Arquivo está vazio");
		}
		System.out.println(" Id do arquivo do livro: " + livro.getArquivo().getId());
		System.out.println("Id do arquivo " + arquivo.getId());
		
		
		arquivo.setId(livro.getArquivo().getId());
		arquivo.setLivro(livro);
		
		System.out.println("Id do arquivo depois da alteração: " + arquivo.getId());
		
		arquivo.setNomeArquivo("Josibel Viado");
		
		//em.merge(arquivo);
		
		em.getTransaction().commit();
		
		
	}
	
}
