package br.com.caelum.livraria.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Perfil;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private List<Perfil> perfis;
	SendMail enviar = new SendMail();

	public SendMail getEnviar() {
		return enviar;
	}

	public void setEnviar(SendMail enviar) {
		this.enviar = enviar;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {

		return new DAO<Usuario>(Usuario.class).listaTodos();
	}

	@PostConstruct
	public void inicializar() {

		this.perfis = Arrays.asList(Perfil.values());

	}

	public void gravar() {

		FacesContext context = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDao().existeEmail(this.usuario);// se o e-mail existir no sistema o valor é true

		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Já existe um usuario com este e-mail, use outro !"));
			// Envia uma Mensagem para a tela
		}

		// Se o id for nulo e o email não está no sistema ele grava o usuario
		if (this.usuario.getId() == null && existe == false) {

			new DAO<Usuario>(Usuario.class).adiciona(this.usuario);
			System.out.println(this.usuario + "Foi cadastrado");
			// this.enviar.sendMail("repositoriodelivrosdigitais@gmail.com",
			// this.getUsuario().getEmail(),
			// "Criação de Conta",
			// "Ola, " + "\n" + "Usuario criado com sucesso, segue abaixo os dados da conta
			// " + "\n" + "USUARIO: "
			// + this.usuario.getEmail() + "\n" + "SENHA: " + this.usuario.getSenha());
		} else if (existe == false) {
			new DAO<Usuario>(Usuario.class).atualiza(this.usuario);
		} else {

		}
		this.usuario = new Usuario();
	}

	public void recebeObjetoUsuario() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.usuario = (Usuario) session.getAttribute("usuario");
	}

	public String formUsuario() {

		return "usuario?faces-redirect=true";
	}
	
	public String formReenviarSenha() {

		return "reenviarSenha?faces-redirect=true";
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
