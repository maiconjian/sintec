package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Ocorrencia;
import br.com.sintec.repository.projection.OcorrenciaMobileProjection;

public interface OcorrenciaRepository extends CrudRepository<Ocorrencia, Long>{
	
	@Query(value="SELECT A.ID AS id,A.DS_DESCRICAO AS descricao,a.ID_CONTRATO AS idContrato,a.ST_ATIVO as ativo  "
			+ "FROM CAD_OCORRENCIA AS A   "
			+ "WHERE A.ID_CONTRATO IN ( "
			+ "	SELECT C.ID_CONTRATO FROM USUARIO_REGIONAL AS B INNER JOIN CAD_REGIONAL AS C ON C.ID = B.ID_REGIONAL "
			+ "	WHERE B.ID_USUARIO = ?1) ",nativeQuery = true)
	List<OcorrenciaMobileProjection> getOcorrenciaMobile(Long idUsuario);

}
