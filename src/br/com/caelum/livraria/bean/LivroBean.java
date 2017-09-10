package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Instanciando a classe Livro
	private Livro livro = new Livro();

	// Declarando atributo
	private Integer autorId;

	// -------------Getters and Setters--------------------------

	// Setando o atribudo ID do autor
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	// Retornando o atribudo ID do autor
	public Integer getAutorId() {
		return autorId;
	}

	// Retorna um livro
	public Livro getLivro() {
		return livro;
	}

	private List<Livro> livros;

	// Lista todos os livros no banco
	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}

		return livros;
	}

	// Lista todos os autores no banco
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	// Retorna o metodo acima, retornando apenas os autores
	// O metodo abaixo fez um desaclopamento
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	// ------------------Metodos-----------------------------------

	// Carrega um livro no update pela ID da classe livro
	public void carregarLivroPelaId() {
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livro.getId());
	}

	// Grava um autor na pagina livro.xhtml
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	// O metodo abaixo grava um livro no banco
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		// O metodo abaixo verifica se o livro possui um autor, caso n�o possua ele n�o
		// permite a grava��o
		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}
		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		// Ao clicar no metodo gravar est� condi��o � ativada, caso o livro j� exista
		// ele � atualizado, caso contrario ele � atualizadoasd
		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	// Remove um livro do banco de dados
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		dao.remove(livro);
		this.livros = dao.listaTodos();
	}

	// Remove um autor do livro na pagina livro.xhtml
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);

	}

	// Esta fun��o � retornada quando se clica em update do livro
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
	}

	// Est� fun��o � chamada quando se clica no cadastrar um autor.
	public String formAutor() {
		System.out.println("Chamanda do formulario do Autor.");
		// O trecho abaixo te redireciona para a pagina de cria��o de autor
		return "autor?faces-redirect=true";
	}

	// Est� fun��o � chamada quando se clica no cadastrar um autor.
	public String formPainelDeLivros() {
		System.out.println("Chamanda do formulario do Autor.");
		// O trecho abaixo te redireciona para a pagina de cria��o de autor
		return "painelLivros?faces-redirect=true";
	}

	// Est� fun��o � chamada quando se clica no cadastrar um autor.
	public String formLivro() {
		System.out.println("Chamanda do formulario do Autor.");
		// O trecho abaixo te redireciona para a pagina de cria��o de autor
		return "livro?faces-redirect=true";
	}

	public String formUsuario() {
		System.out.println("Chamanda do formulario do Autor.");
		// O trecho abaixo te redireciona para a pagina de cria��o de autor
		return "usuario?faces-redirect=true";
	}

	// Esta fun��o obriga que no formulario o ISBN comece com o digito um
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		// Verifica se o o que foi digitado come�a com um
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			// Informa que o ISBN deve come�ar com um
			throw new ValidatorException(new FacesMessage("ISBN deve come�ar com 1"));
		}

	}

	/* Assim que o usuario clica em avaliacao � guardado o objeto livro
	 * e enviado para a pagina ratingView
	 */
	public String enviaObjeto(Livro livro) {

		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("livroId", livro);
		
		return "avaliacao?faces-redirect=true";
	}
}
