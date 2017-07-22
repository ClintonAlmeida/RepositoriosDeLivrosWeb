package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

//O managedBean diz que está classe gerencia uma pagina xhtml, ela é o Bean de autor
@ManagedBean
// O viewScoped faz com que está pagina não precise de um request para atualizar
// suas informações
@ViewScoped
public class AutorBean {

	// instanciando classe autor
	private Autor autor = new Autor();
	// declarando um atributo para usar como id de autor
	private Integer autorId;

	// ---------------getters e setters---------------------------
	// Retorna Autor
	public Autor getAutor() {
		return autor;
	}

	// Seta um autor
	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	// Retorna o atributo declado no começo do codigo
	public Integer getAutorId() {
		return autorId;
	}

	// Seta o atributo ID
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	// -----------------------Metodos-------------------------------------

	// Quando se clica em update na pagina autor
	// ele é carregado na pagina
	public void carregarAutorPelaId() {
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	// Grava o autor no banco de dados
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		// Verifica se autor existe, se ele não existir ele é salvo no banco
		// caso contrario ele é atualizado
		// está função está sendo usado no metodo update
		if (this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}

		this.autor = new Autor();

		// Assim que o autor é gravado no banco o botão gravar o redireciona para a
		// pagina
		// de livros
		return "livro?faces-redirect=true";
	}

	// Remove um autor selecionado pela id
	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
		new DAO<Autor>(Autor.class).remove(autor);
	}

	// Retorna a lista de todos os autores
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

}
