package br.com.caelum.livraria.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Arquivo;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Instanciando a classe Livro
	private Livro livro = new Livro();
	private Usuario usuario = new Usuario();
	private UploadedFile uploadedFile = null;
	private Arquivo arquivo = new Arquivo();
	private Livro livroTemp = new Livro();

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Livro getLivroTemp() {
		return livroTemp;
	}

	public boolean existeLivro(Livro livro) {

		if (this.livro.getId() == null) {
			return false;

		} else {
			return true;
		}

	}

	public void setLivroTemp(Livro livroTemp) {
		this.livroTemp = livroTemp;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

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
			System.out.println(this.livro.getTitulo());
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
	//Retorna a Media de avaliacoes(a soma total de avaliacoes / por quantidade de avaliacoes)
	public Long retornaMedia(Livro livro) {
		
		LivroDao livroDao = new LivroDao();
		return livroDao.retornaMediaAvaliacao(livro);
	}

	// Grava um autor na pagina livro.xhtml
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public void upload() {

		if (uploadedFile != null) {

			try {

				File file = new File(diretorioRaiz(), uploadedFile.getFileName());
				DAO<Arquivo> daoArquivo = new DAO<Arquivo>(Arquivo.class);
				
				if(uploadedFile.getFileName().endsWith(".PNG") || uploadedFile.getFileName().endsWith(".png")) {
					System.out.println("� UM ARQUIVO EM JPG");
					
					
				}
				

				arquivo.setNomeArquivo(uploadedFile.getFileName());
				arquivo.setCaminhoArquivo(diretorioRaiz());
				arquivo.setTamanhoArquivo(uploadedFile.getSize());
				arquivo.setLivro(livro);

				arquivo.setLivro(livro);
				System.out.println("Id do livro " + this.livro.getId());

				daoArquivo.adiciona(arquivo);

				OutputStream out = new FileOutputStream(file);
				out.write(uploadedFile.getContents());
				out.close();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Upload completo", "O arquivo " + uploadedFile.getFileName() + " foi salvo!"));
			} catch (IOException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "O arquivo n�o foi salvo", e.getMessage()));
			}

		} else {
			return;
		}
	}

	// O metodo abaixo grava um livro no banco
	public String gravar() {

		System.out.println("Gravando livro " + this.livro.getTitulo());
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		System.out.println("Nome do novo arquivo " + arquivo.getNomeArquivo());

		// O metodo abaixo verifica se o livro possui um autor, caso n�o possua ele n�o
		// permite a grava��o
		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return null;
		}

		// Ao clicar no metodo gravar est� condi��o � ativada, caso o livro j� exista
		// ele � atualizado, caso contrario ele � atualizadoasd

		if (this.livro.getId() == null) {

			dao.adiciona(this.livro);

			return this.formLivroAtualizar();

		} else {

			livro.setArquivo(arquivo);
			dao.atualiza(this.livro);
			this.livro = new Livro();
		}

		return "listaDeLivros?faces-redirect=true";

	}

	private String diretorioRaiz() {

		File file = new File("Repositorio de Livros Web");
		
		file.mkdir();
		
		return "C:\\Ha";
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

	public String formLivroAtualizar() {
		System.out.println("Chamanda do formulario do Autor.");
		// O trecho abaixo te redireciona para a pagina de cria��o de autor
		return "livroAtualizar?faces-redirect=true";
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

	/*
	 * Assim que o usuario clica em avaliacao � guardado o objeto livro e enviado
	 * para a pagina ratingView
	 */
	public String enviaObjeto(Livro livro) {

		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		session.setAttribute("livroId", livro);
		System.out.println("Livro � o  " + livro.getTitulo());

		return "avaliacao?faces-redirect=true";
	}

	public void recebeObjeto() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		this.livro = (Livro) session.getAttribute("livroId");
		this.usuario = (Usuario) session.getAttribute("usuario");
	}
	


}
