package br.com.caelum.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Comentario;
import br.com.caelum.livraria.modelo.Livro;

public class LivroDao {

	EntityManager em = new JPAUtil().getEntityManager();

	public List<Comentario> devolveLista(Livro livro) {

		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Comentario> query = em.createQuery(
				"Select u FROM Comentario u where livro_id = :pId", Comentario.class);

		query.setParameter("pId", livro.getId());

		List<Comentario> comentario = query.getResultList();

		// Compara se existe o e-mail cadastrado na base de dados do sistema

		em.close();

		return comentario;
	}
}
