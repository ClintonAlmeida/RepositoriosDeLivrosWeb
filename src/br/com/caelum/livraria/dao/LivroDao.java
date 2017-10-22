package br.com.caelum.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Comentario;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;

public class LivroDao {

	EntityManager em = new JPAUtil().getEntityManager();

	public List<Comentario> devolveLista(Livro livro) {

		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Comentario> query = em.createQuery("Select u FROM Comentario u where livro_id = :pId",
				Comentario.class);

		query.setParameter("pId", livro.getId());

		List<Comentario> comentario = query.getResultList();

		// Compara se existe o e-mail cadastrado na base de dados do sistema

		em.close();

		return comentario;
	}

	public Long retornaMediaAvaliacao(Livro livro) {

		EntityManager em = new JPAUtil().getEntityManager();

		// Retorna a media = soma de todas as avaliacao / pela quantidade de avaliacao
		// do respectivo livro
		String sql = "Select Round(sum(avaliacaoPorComentario)/count(avaliacaoPorComentario)) "
				+ "from Comentario where livro_id = :pId";

		Query query = em.createQuery(sql);

		query.setParameter("pId", livro.getId());

		Long media =  (Long)query.getSingleResult();
		
		return media;
	}
	
	public List<Livro> retornaLivrosPorUsuario(Usuario usuario) {

		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Livro> query = em.createQuery("Select u FROM Livro u where usuario_id = :pId",
				Livro.class);

		query.setParameter("pId", usuario.getId());

		List<Livro> livros = query.getResultList();

		// Compara se existe o e-mail cadastrado na base de dados do sistema

		em.close();

		return livros;
	}

}
