package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.CategoriaTipoServico;

public interface CategoriaTipoServicoRepository extends CrudRepository<CategoriaTipoServico, Long>{
	
	@Query(value="SELECT * FROM CAD_CATEGORIA_TIPO_SERVICO WHERE ST_ATIVO = ?1",nativeQuery = true)
	public List<CategoriaTipoServico> pesquisar(int ativo);

}
