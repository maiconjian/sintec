package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Contrato;
import br.com.sintec.repository.impl.contrato.ContratoRepositoryQuery;
import br.com.sintec.repository.projection.ContratoUsuarioProjection;

public interface ContratoRepository extends CrudRepository<Contrato, Long>,ContratoRepositoryQuery {

	
	@Query(value="SELECT C.ID AS idContrato,C.NM_NOME AS nomeContrato FROM CAD_REGIONAL AS A "
			   + "INNER JOIN USUARIO_REGIONAL AS B ON B.ID_REGIONAL = A.ID "
			   + "INNER JOIN CAD_CONTRATO AS C ON C.ID = A.ID_CONTRATO "
			   + "WHERE B.ID_USUARIO = ?1 AND C.ST_ATIVO = 1 "
			   + "GROUP BY C.ID, C.NM_NOME ",nativeQuery = true)
	public List<ContratoUsuarioProjection> listaContratoUsuario(Long idUsuario);
}
