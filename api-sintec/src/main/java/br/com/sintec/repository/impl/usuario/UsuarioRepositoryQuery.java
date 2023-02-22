package br.com.sintec.repository.impl.usuario;

import java.util.List;

import br.com.sintec.model.Usuario;
import br.com.sintec.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	
	List<Usuario> pesquisar(UsuarioFilter filter);

}
