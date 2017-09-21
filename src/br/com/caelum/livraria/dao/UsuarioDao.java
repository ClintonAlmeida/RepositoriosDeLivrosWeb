package br.com.caelum.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao {

	public boolean existe(Usuario usuario) {

		EntityManager em = new JPAUtil().getEntityManager();

		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Usuario> query = em.createQuery(" select u from Usuario u "
				+ " where (u.email = :pEmail and u.senha = :pSenha) and " + "(u.ativo = :pAtivo)", Usuario.class);

		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		query.setParameter("pAtivo", usuario.isAtivo());
		try {
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		em.close();

		return true;
	}

	public boolean existeEmail(Usuario usuario) {

		EntityManager em = new JPAUtil().getEntityManager();

		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Usuario> query = em.createQuery(" Select u FROM Usuario u WHERE u.ativo = true and u.ativo = :pAtivo", Usuario.class);

		query.setParameter("pAtivo", usuario.isAtivo());
		
		List<Usuario> usuarios = query.getResultList();
		
		
		//Compara se existe o e-mail cadastrado na base de dados do sistema
		for (Usuario usuario2 : usuarios) {
			
			if(usuario2.getEmail().equals(usuario.getEmail())) {
				
				System.out.println(usuario2.getEmail());
				
				return true;
			}
		}

		em.close();

		return false;
	}

	public Usuario retornaUsuario(Usuario usuario) {

		EntityManager em = new JPAUtil().getEntityManager();

		// Verifica se o email e senha do usuario existe no sistema
		TypedQuery<Usuario> query = em.createQuery(" Select u FROM Usuario u WHERE u.email = :pEmail", Usuario.class);

		query.setParameter("pEmail", usuario.getEmail());
		
		Usuario usuarios = query.getSingleResult();
		
				

		em.close();

		return usuarios;
	}

	
}
