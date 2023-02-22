package br.com.sintec.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sintec.mail.Mailer;
import br.com.sintec.model.Usuario;
import br.com.sintec.model.helpers.PerfilEnum;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.UsuarioRepository;
import br.com.sintec.repository.filter.UsuarioFilter;
import br.com.sintec.repository.projection.UsuarioMobileProjection;
import br.com.sintec.util.Apoio;

@Service
public class UsuarioService implements IGenericService<Usuario, UsuarioFilter, Serializable>{


	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;

	@Override
	public Usuario incluir(Usuario entity) throws Exception {
			String senha = Apoio.gerarSenhaAleatoria(6);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			entity.setSenha(encoder.encode(senha));
			entity.setDataAtualizacao(Apoio.getDateTimeFuso());
			entity.setAtivo(SituacaoEnum.ATIVO.getValue());
			entity.setPin(entity.getMatricula());
			Usuario usuario =  this.usuarioRepository.save(entity);
			if(usuario.getPerfil().getId() !=  PerfilEnum.MOBILE.getValue()) {
				enviarSenha(usuario, senha);
			}
			return usuario;
		
	}

	@Override
	public Usuario alterar(Usuario entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setPin(entity.getMatricula());
		Usuario UsuarioAlterado = this.usuarioRepository.save(entity);
		return UsuarioAlterado;
	}

	@Override
	public Usuario buscarPorId(long id) {
		Optional<Usuario> UsuarioEncontrado = this.usuarioRepository.findById(id);
		return UsuarioEncontrado.get();
	}

	@Override
	public List<Usuario> pesquisar(UsuarioFilter filter) {
		return this.usuarioRepository.pesquisar(filter);
	}
	
	private boolean enviarSenha(Usuario usuario,String senha) {
		List<Usuario> destinatarios = new ArrayList<>();
		destinatarios.add(usuario);

		this.mailer.enviarSenha(senha, usuario, destinatarios, "Login de Acesso");
		return true;
	}
	
	public Usuario resetPassword(long id) throws Exception {
		Usuario usuario = buscarPorId(id);
		if (usuario != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senha = Apoio.gerarSenhaAleatoria(6);
			usuario.setSenha(encoder.encode(senha.trim()));
			usuarioRepository.save(usuario);
			this.enviarSenha(usuario, senha);
			usuario.setSenha("");
			return usuario;
		} else {
			return null;
		}
	}
	
//	public Usuario alterarSenha(Usuario entity) {
//		Usuario usuario = this.usuarioRepository.findById(entity.getId()).get();
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		usuario.setSenha(encoder.encode(entity.getSenha()));
//		usuario = this.usuarioRepository.save(usuario);
//		usuario.setSenha("");
//		return usuario;
//	}
	
//	MOBILE
	
	public List<UsuarioMobileProjection> getUsuarioMobile(){
		return this.usuarioRepository.getUsuarioMobile();
	}

}
