package br.com.sintec.seguranca;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.sintec.exception.CustomRuntimeException;
import br.com.sintec.model.Usuario;
import br.com.sintec.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) {
		Optional<Usuario> usuarioOptional = this.usuarioRepository.buscarLogin(login);
		
		if(usuarioOptional.isPresent()) {
			try {
				Usuario usuario = usuarioOptional.get();
				return new UsuarioSistema(usuario, getPermissoes(usuario));
			} catch (Exception e) {
				throw new  CustomRuntimeException("USUARIO OU SENHA INVALIDOS!");
			}
			
		}else {
			throw new CustomRuntimeException("USUARIO NAO ENCONTRADO OU DESATIVADO!");
		}
		
		
	}
	
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getNome().toUpperCase())));
		return authorities;
	}
}
