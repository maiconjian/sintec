package br.com.sintec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.RetornoResposta;
import br.com.sintec.repository.projection.RetornoRespostaProjection;

public interface RetornoRespostaRepository  extends CrudRepository<RetornoResposta, Long>{
	
	@Query(value="SELECT * FROM SERV_RETORNO_RESPOSTA WHERE ID_SERVICO = ?1 AND ID_PERGUNTA = ?2 ",nativeQuery = true)
	public Optional<RetornoResposta> buscarRetornoResposta(Long idServico,Long idPergunta);
	
	@Query(value="SELECT D.NM_TITULO AS tituloQuestionario,B.NM_ENUNCIADO AS enunciado,C.NM_DESCRICAO AS resposta,   "
			+ "			  C.FLAG_NCONF AS nconf FROM SERV_RETORNO_RESPOSTA AS A   "
			+ "			  INNER JOIN CAD_PERGUNTA AS B ON B.ID = A.ID_PERGUNTA   "
			+ "			  INNER JOIN CAD_ALTERNATIVA AS C ON C.ID = A.ID_ALTERNATIVA   "
			+ "			  INNER JOIN CAD_QUESTIONARIO AS D ON D.ID = B.ID_QUESTIONARIO   "
			+ "			  WHERE A.ID_SERVICO = ?1" ,nativeQuery = true)
	public List<RetornoRespostaProjection> getRespostas(Long idServico); 

}
