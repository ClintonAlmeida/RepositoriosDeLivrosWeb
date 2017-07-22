package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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

	// Lista todos os livros no banco
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
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

		// O metodo abaixo verifica se o livro possui um autor, caso não possua ele não
		// permite a gravação
		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		// Ao clicar no metodo gravar está condição é ativada, caso o livro já exista
		// ele é atualizado, caso contrario ele é atualizado
		if (this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	// Remove um livro do banco de dados
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		new DAO<Livro>(Livro.class).remove(livro);
	}

	// Remove um autor do livro na pagina livro.xhtml
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	// Esta função é retornada quando se clica em update do livro
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
	}

	// Está função é chamada quando se clica no cadastrar um autor.
	public String formAutor() {
		System.out.println("Chamanda do formulÃ¡rio do Autor.");
		// O trecho abaixo te redireciona para a pagina de criação de autor
		return "autor?faces-redirect=true";
	}

	// Esta função obriga que no formulario o ISBN comece com o digito um
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		// Verifica se o o que foi digitado começa com um
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			// Informa que o ISBN deve começar com um
			throw new ValidatorException(new FacesMessage("ISBN deveria comeÃ§ar com 1"));
		}

	}
}
