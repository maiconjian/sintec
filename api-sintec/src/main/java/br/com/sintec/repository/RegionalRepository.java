package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Regional;
import br.com.sintec.repository.impl.regional.RegionalRepositoryQuery;
import br.com.sintec.repository.projection.RegionalUsuarioProjection;

public interface RegionalRepository  extends CrudRepository<Regional, Long>,RegionalRepositoryQuery{
	
	@Query(value="SELECT A.ID AS idRegional,A.NM_NOME AS nomeRegional,A.ID_CONTRATO AS idContrato FROM CAD_REGIONAL AS A "
			+ "INNER JOIN USUARIO_REGIONAL AS B ON B.ID_REGIONAL = A.ID "
			+ "INNER JOIN CAD_CONTRATO AS C ON C.ID = A.ID_CONTRATO "
			+ "WHERE B.ID_USUARIO = ?1 AND A.ST_ATIVO = 1  AND C.ST_ATIVO = 1",nativeQuery = true)
	public List<RegionalUsuarioProjection> listaRegionalUsuario(Long idUsuario);

}
