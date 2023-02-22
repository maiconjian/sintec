package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.PerfilAcesso;

public interface PerfilAcessoRepository extends CrudRepository<PerfilAcesso, Long> {

	
	@Query(value="SELECT * FROM SYS_PERFIL_ACESSO "
				+"WHERE ST_ATIVO = ?1 ",nativeQuery = true)
	public List<PerfilAcesso> pesquisar(int ativo);
}
