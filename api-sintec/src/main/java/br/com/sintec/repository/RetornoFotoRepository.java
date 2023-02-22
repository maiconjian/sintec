package br.com.sintec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.RetornoFoto;
import br.com.sintec.repository.projection.RetornoFotoProjection;

public interface RetornoFotoRepository extends CrudRepository<RetornoFoto, Long> {
	
	@Query(value="SELECT * FROM SERV_RETORNO_FOTO WHERE ID_SERVICO = ?1 AND NM_NOME = ?2 ",nativeQuery = true)
	public Optional<RetornoFoto> buscarRetornoFoto(Long idServico,String nome);
	
	@Query(value="SELECT A.DS_PATH AS path,A.DS_OBSERVACAO as observacao, FLAG_ASSINATURA as flagAssinatura,B.ID as idQuestionario, B.NM_TITULO as titulo FROM SERV_RETORNO_FOTO AS A "
			+ "LEFT OUTER JOIN CAD_QUESTIONARIO AS B ON B.ID = A.ID_QUESTIONARIO "
			+ "WHERE A.ID_SERVICO = ?1 ",nativeQuery = true)
	public List<RetornoFotoProjection> getRetornoFoto(Long idServico);

}

