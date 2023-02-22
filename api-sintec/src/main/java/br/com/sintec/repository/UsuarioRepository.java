package br.com.sintec.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Usuario;
import br.com.sintec.repository.impl.usuario.UsuarioRepositoryQuery;
import br.com.sintec.repository.projection.UsuarioMobileProjection;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>,UsuarioRepositoryQuery {

//	Optional<Usuario> findByLogin(String login);
	
	@Transactional
	@Query(value="SELECT * FROM SYS_USUARIO AS A " + 
				 "WHERE A.NM_LOGIN = ?1 AND A.ST_ATIVO = 1 ",nativeQuery = true)
	public Optional<Usuario> buscarLogin(String login);
	
	@Query(value="SELECT ID AS id,NM_NOME AS nome,NR_MATRICULA AS matricula,NR_PIN AS pin,ST_ATIVO AS ativo FROM SYS_USUARIO ",nativeQuery = true)
	public List<UsuarioMobileProjection> getUsuarioMobile();
	
}
