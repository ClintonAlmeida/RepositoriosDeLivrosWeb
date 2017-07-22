package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	/*
	 * Classe responsavel pelas permissões dos usuarios no sistema
	 * 
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */

	@Override
	public void afterPhase(PhaseEvent evento) {

		// Pega o log da pagina atual que o usuario está logado
		FacesContext context = evento.getFacesContext();
		// Transforma este log em uma String
		String nomePagina = context.getViewRoot().getViewId();
		// Imprimi o nome da pagina
		System.out.println(nomePagina);

		// Verifica se o usuario está na pagina login.xhtml, caso ele esteja não faz
		// nada
		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}

		// Pega o usuario atual que está logado no sistema e atribui uma id para o mesmo
		// com o valor usuarioLogado
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		// Se a id="usuarioLogado" não tiver nenhum valor significa que não tem ninguem
		// logado na pagina
		if (usuarioLogado != null) {
			return;
		}

		// redirecionamento para login.xhtml

		//Redireciona o usuario para a pagina de Login
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
