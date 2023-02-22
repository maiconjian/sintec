package br.com.sintec.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.TipoServico;
import br.com.sintec.repository.impl.tiposervico.TipoServicoRepositoryQuery;

public interface TipoServicoRepository extends CrudRepository<TipoServico, Long>,TipoServicoRepositoryQuery {
	
}
