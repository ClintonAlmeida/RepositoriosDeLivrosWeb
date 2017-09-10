package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@SessionScoped
public class RatingView {

	private Integer rating3;

	private Livro livro = new Livro();

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	 /* 
	 * Recebe o objeto de livroBean e deixa disponivel para uso
	 */
	public void recebeObjeto() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.livro = (Livro) session.getAttribute("livroId");

	}

	public String gravarAvaliacao() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		// O metodo abaixo verifica se o livro possui um autor, caso não possua ele não
		// permite a gravação

		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		// Ao clicar no metodo gravar está condição é ativada, caso o livro já exista
		// ele é atualizado, caso contrario ele é atualizadoasd
		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);

		} else {
			dao.atualiza(this.livro);
		}

		return "livro?faces-redirect=true";
	}

	/*
	 * O metodo retorna null para assim que a pagina for carregada as estrelas estão
	 * em branco
	 */
	public Integer getRating3() {
		return null;
	}

	/*
	 * Se o livro nunca foi avaliado ou seja se a avaliacao for nula entao é
	 * atribuido o valor de estrelas atual se o valor não for nulo soma o valor
	 * atual com o novo
	 */
	public void setRating3(Integer rating3) {

		if (this.livro.getAvaliacao() == null) {
			this.livro.setAvaliacao(rating3);
		} else {
			Integer temp = this.livro.getAvaliacao() + rating3;
			this.livro.setAvaliacao(temp);

		}

	}

}
