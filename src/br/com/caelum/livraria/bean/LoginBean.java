package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	// Instanciando a classe usuario
	private Usuario usuario = new Usuario();

	// -------------------Getters and Setters-------------------------------
	// Retorna o usuario
	public Usuario getUsuario() {
		return usuario;
	}

	// Está função é chamada quando clica se em login.
	public String efetuaLogin() {
		System.out.println("fazendo login do usuario " + this.usuario.getEmail());

		FacesContext context = FacesContext.getCurrentInstance();
		// Booleano que verifica se o usuario está logado sim ou nao.
		boolean existe = new UsuarioDao().existe(this.usuario);

		// Se o usuario estiver logado no sistema ele é redirecionado para a pagina de
		// livros
		if (existe) {
			// a linha de codigo abaixo usa um MapList que tem como id = "usuarioLogado" e
			// valor = e o usuario atual do sistema
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			// O usuario é redirecionado para a pagina Livros.xhtml
			return "livro?faces-redirect=true";
		}

		// As mensagens de usuario não encontrado duram apenas duas requisições
		context.getExternalContext().getFlash().setKeepMessages(true);
		// Caso o login ou senha esteja incorreto manda uma mensagem dizendo que o
		// usuario não foi encontrado
		context.addMessage(null, new FacesMessage("UsuÃ¡rio nÃ£o encontrado"));
		// O usuario é redirecionado para a pagina de login novamente, ele nunca vai
		// conseguir acessar sem realizar um login
		return "login?faces-redirect=true";
	}

	// Função responsavel por deslogar o usuario do sistema
	public String deslogar() {

		FacesContext context = FacesContext.getCurrentInstance();
		// Procura no MapList a id com o valor usuarioLogado e remove ela do sistema
		// derrubando o usuario do sistema
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		//Novamente o usuario é redirecionado para a pagina de login
		return "login?faces-redirect=true";
	}
}
