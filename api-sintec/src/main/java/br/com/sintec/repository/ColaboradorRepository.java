package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Colaborador;
import br.com.sintec.repository.impl.colaborador.ColaboradorRepositoryQuery;
import br.com.sintec.repository.projection.ColaboradorProjection;

public interface ColaboradorRepository extends CrudRepository<Colaborador, Long>,ColaboradorRepositoryQuery {


	@Query(value="SELECT A.ID AS id,A.NM_NOME AS nome FROM CAD_COLABORADOR AS A "
			+ "LEFT OUTER JOIN CAD_VEICULO AS B ON B.ID_COLABORADOR = A.ID "
			+ "WHERE B.ID IS NULL "
			+ "AND A.ID_REGIONAL = ?1 "
			+ "AND A.ST_ATIVO = 1 ",nativeQuery = true)
	public List<ColaboradorProjection> listaColaboradorSemVeiculo(Long idRegional);
}
