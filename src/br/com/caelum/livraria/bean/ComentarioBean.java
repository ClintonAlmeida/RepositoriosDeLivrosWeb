package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Comentario;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class ComentarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Instanciando a classe Livro
	private Usuario usuario = new Usuario();

	public void recebeObjeto() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.usuario = (Usuario) session.getAttribute("usuario");
	}

	public void gravarComentario(Comentario comentario) {

		
		DAO<Comentario> dao = new DAO<Comentario>(Comentario.class);

		dao.adiciona(comentario);
		System.out.println("O comentario diz: " + comentario.getMensagem());
	}
	
	

}
