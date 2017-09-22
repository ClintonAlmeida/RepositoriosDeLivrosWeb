package br.com.caelum.livraria.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.dao.JPAUtil;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;

public class TestaBanco {
	
	
	//Classe criada para testar consultar do JPQL
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		Livro livro = new Livro();
		Usuario usuario = new Usuario();	
		
		livro.setId(3);
		
		//teste
		
		System.out.println("teste");
		
		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Livro> query = em.createQuery("Select distinct u FROM Livro u join fetch u.comentarios c WHERE u.id = :pId", Livro.class);

		query.setParameter("pId", livro.getId());
		
		List<Livro> livros = query.getResultList();
		
		
		//Compara se existe o e-mail cadastrado na base de dados do sistema
		for (Livro livro2 : livros) {
			
			System.out.println(livro2.getTitulo());
			System.out.println(livro2.getComentarios());
		}

		em.close();

		
		
	}

}
