package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class ConfigUsuarioBean {

	SendMail enviar = new SendMail();
	private Usuario usuario = new Usuario();
	private String senhaAtual;
	private String novaSenha;
	private String confirmaSenha;

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

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

	public void redefinirSenha(FacesContext fc, UIComponent component, String senhaAtual)
			throws ValidatorException {

		recebeUsuario();

		// Verifica se o o que foi digitado começa com um
		if (!this.usuario.getSenha().equals(senhaAtual)) {
			// Informa que o ISBN deve começar com um
			throw new ValidatorException(new FacesMessage("Senha invalida"));
		}

	}

	public void comparaNovaSenha() {

		System.out.println("Nova Senha: " + this.novaSenha);
		System.out.println("Confirmação de senha " + this.confirmaSenha);
		
		// Verifica se o o que foi digitado começa com um
		if (!this.novaSenha.equals(confirmaSenha)) {
			System.out.println("Chamou a função");
			// Informa que o ISBN deve começar com um
			System.out.println("SEnhas não conferem");
			throw new ValidatorException(new FacesMessage("As senhas não são iguais"));
		}

	}

	public void comparaSenhaInicial(FacesContext fc, UIComponent component, String senhaAtual)
			throws ValidatorException {

		recebeUsuario();

		// Verifica se o o que foi digitado começa com um
		if (!this.usuario.getSenha().equals(senhaAtual)) {
			// Informa que o ISBN deve começar com um
			throw new ValidatorException(new FacesMessage("Senha invalida"));
		}

	}

	public void recebeUsuario() {
		
		recebeObjeto();
		UsuarioDao usuarioDao = new UsuarioDao();
		this.usuario = usuarioDao.retornaUsuario(usuario);
	}

	public void reenviarSenha(String email) {

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

	public void recebeObjeto() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.usuario = (Usuario) session.getAttribute("usuario");
	}

	public String formAlterarSenha() {

		return "alterarSenha?faces-redirect=true";
	}

}
