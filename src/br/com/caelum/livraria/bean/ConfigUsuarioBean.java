package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;


@ManagedBean
@ViewScoped
public class ConfigUsuarioBean {
	
	SendMail enviar = new SendMail();
	private Usuario usuario = new Usuario();
	
	

	public SendMail getEnviar() {
		return enviar;
	}



	public void setEnviar(SendMail enviar) {
		this.enviar = enviar;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public void redefinirSenha(String email) {

		try {

			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			UsuarioDao usuarioDao = new UsuarioDao();

			Usuario user = usuarioDao.retornaUsuario(usuario);
			this.enviar.sendMail("repositoriodelivrosdigitais@gmail.com", email, "SENHA DE ACESSO",
					"Olá, " + "\n" + "Segue abaixo os dados para " + "acesso ao sistema: " + "\n" + "e-mail: "
							+ user.getEmail() + "\n" + "senha: " + user.getSenha());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha enviada para o e-mail: " + email, ""));

		} catch (NoResultException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Endereço de e-mail não encontrado", ""));

		}

	}

}
