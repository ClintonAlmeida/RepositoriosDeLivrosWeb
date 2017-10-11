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
	 * Classe responsavel pelas permiss�es dos usuarios no sistema
	 * 
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */

	@Override
	public void afterPhase(PhaseEvent evento) {

		// Pega o log da pagina atual que o usuario est� logado
		FacesContext context = evento.getFacesContext();
		// Transforma este log em uma String
		String nomePagina = context.getViewRoot().getViewId();
		// Imprimi o nome da pagina
		System.out.println(nomePagina);

		// Verifica se o usuario est� na pagina login.xhtml, caso ele esteja n�o faz
		// nada
		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}


		// Pega o usuario atual que est� logado no sistema e atribui uma id para o mesmo
		// com o valor usuarioLogado
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

		// Se a id="usuarioLogado" n�o tiver nenhum valor significa que n�o tem ninguem
		// logado na pagina
		if (usuarioLogado != null) {
			return;
		}

		// redirecionamento para login.xhtml

		// Redireciona o usuario para a pagina de Login
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
