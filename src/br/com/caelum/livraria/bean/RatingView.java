package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Comentario;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class RatingView {

	private Integer rating3;
	private Usuario usuario = new Usuario();
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	public List<Comentario> getComentarios() {

		LivroDao comentario = new LivroDao();
		comentarios = comentario.devolveLista(this.livro);

		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private Livro livro = new Livro();
	private Comentario comentario = new Comentario();

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

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
		this.usuario = (Usuario) session.getAttribute("usuario");
	}

	public String gravarAvaliacao() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		// O metodo abaixo verifica se o livro possui um autor, caso não possua ele não
		// permite a gravação

		salvaComentario();

		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		// Ao clicar no metodo gravar está condição é ativada, caso o livro já exista
		// ele é atualizado, caso contrario ele é atualizadoasd
		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);

		} else {
			dao.atualiza(this.livro);

		}

		return "exibirComentarios?faces-redirect=true";

	}

	public void salvaComentario() {

		ComentarioBean bean = new ComentarioBean();
		Usuario usuarioNovo = new UsuarioDao().retornaUsuario(this.usuario);// Retorna o usuario da base de dados
		comentario.setAutorDoComentario(usuarioNovo.getNome());// Setando o autor do comentario
		//comentario.setAvaliacaoPorComentario(rating3);
		comentario.setLivro(this.livro);

		if (!this.livro.getComentarios().isEmpty()) {

			this.livro.adicionaComentario(comentario);
			bean.gravarComentario(comentario);

		} else {

			this.livro.adicionaComentario(comentario);
			bean.gravarComentario(comentario);
		}
		this.comentario = new Comentario();
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

		comentario.setAvaliacaoPorComentario(rating3);
		if (this.livro.getAvaliacao() == null) {
			this.livro.setAvaliacao(rating3);
		} else {
			Integer temp = this.livro.getAvaliacao() + rating3;
			this.livro.setAvaliacao(temp);

		}

	}

	
	public void apenasMilLetras(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		// Verifica se o o que foi digitado começa com um
		
		String valor = value.toString();
		if (valor.length() >= 1000) {
			// Informa que o ISBN deve começar com um
			throw new ValidatorException(new FacesMessage("Voce só pode inserir mil caracteres"));
		}

	}

}
