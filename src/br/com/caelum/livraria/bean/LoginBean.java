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

	// Est� fun��o � chamada quando clica se em login.
	public String efetuaLogin() {
		System.out.println("fazendo login do usuario " + this.usuario.getEmail());

		FacesContext context = FacesContext.getCurrentInstance();
		// Booleano que verifica se o usuario est� logado sim ou nao.
		boolean existe = new UsuarioDao().existe(this.usuario);

		// Se o usuario estiver logado no sistema ele � redirecionado para a pagina de
		// livros
		if (existe) {
			// a linha de codigo abaixo usa um MapList que tem como id = "usuarioLogado" e
			// valor = e o usuario atual do sistema
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			// O usuario � redirecionado para a pagina Livros.xhtml
			return "livro?faces-redirect=true";
		}

		// As mensagens de usuario n�o encontrado duram apenas duas requisi��es
		context.getExternalContext().getFlash().setKeepMessages(true);
		// Caso o login ou senha esteja incorreto manda uma mensagem dizendo que o
		// usuario n�o foi encontrado
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		// O usuario � redirecionado para a pagina de login novamente, ele nunca vai
		// conseguir acessar sem realizar um login
		return "login?faces-redirect=true";
	}

	// Fun��o responsavel por deslogar o usuario do sistema
	public String deslogar() {

		FacesContext context = FacesContext.getCurrentInstance();
		// Procura no MapList a id com o valor usuarioLogado e remove ela do sistema
		// derrubando o usuario do sistema
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		//Novamente o usuario � redirecionado para a pagina de login
		return "login?faces-redirect=true";
	}
}
