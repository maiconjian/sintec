package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Imovel;
import br.com.sintec.repository.impl.localidade.ImovelRepositoryQuery;

public interface ImovelRepository extends CrudRepository<Imovel, Long>,ImovelRepositoryQuery{
	
	@Query(value="SELECT * FROM CAD_LOCALIDADE WHERE ID_REGIONAL = ?1 AND ST_ATIVO = 1 ",nativeQuery = true)
	public List<Imovel> listaLocalidadePorRegional(Long idRegional);

}
