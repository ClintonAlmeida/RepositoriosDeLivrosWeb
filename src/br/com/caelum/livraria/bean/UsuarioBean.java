package br.com.caelum.livraria.bean;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private List<String> perfis = Arrays.asList("Autor", "Leitor");
	SendMail enviar = new SendMail();

	public SendMail getEnviar() {
		return enviar;
	}

	public void setEnviar(SendMail enviar) {
		this.enviar = enviar;
	}

	public List<String> getPerfis() {
		return perfis;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {

		return new DAO<Usuario>(Usuario.class).listaTodos();
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.usuario.getEmail());

		// Verifica se autor existe, se ele não existir ele é salvo no banco
		// caso contrario ele é atualizado
		// está função está sendo usado no metodo update
		if (this.usuario.getId() == null) {
			new DAO<Usuario>(Usuario.class).adiciona(this.usuario);
			this.enviar.sendMail("repositoriodelivrosdigitais@gmail.com", this.getUsuario().getEmail(),
					"Criação de Conta",
					"Ola, " + "\n" + "Usuario criado com sucesso, segue abaixo os dados da conta " + "\n" + "USUARIO: "
							+ this.usuario.getEmail() + "\n" + "SENHA: " + this.usuario.getSenha());
		} else {
			new DAO<Usuario>(Usuario.class).atualiza(this.usuario);
		}

		this.usuario = new Usuario();
	}
}
