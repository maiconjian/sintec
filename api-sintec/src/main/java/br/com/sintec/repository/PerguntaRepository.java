package br.com.sintec.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Pergunta;
import br.com.sintec.repository.projection.PerguntaMobileProjection;

public interface PerguntaRepository extends CrudRepository<Pergunta, Long>{
	
	@Query(value="SELECT * FROM CAD_PERGUNTA AS A "
			   + "WHERE A.ID_QUESTIONARIO = ?1 "
			   +"ORDER BY ST_ATIVO DESC",nativeQuery = true)
	List<Pergunta> listaPerguntaQuestionario(Long idQuestionario);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE CAD_PERGUNTA SET DT_ATUALIZACAO = ?1 ,ST_ATIVO = 0 WHERE ID_QUESTIONARIO = ?2 ",nativeQuery = true)
	public void desativarPerguntasQuestionario(LocalDateTime dataAtualizacao,Long idQuestionario);
	
	@Query(value="SELECT A.ID AS id,A.NM_ENUNCIADO AS enunciado,A.NR_ORDEM_APRESENTACAO AS ordemApresentacao,A.FLAG_FOTO AS flagFoto,A.FLAG_MULTIPLA_ESCOLHA AS flagMultiplaEscolha, "
			+ "A.FLAG_OBRIGATORIO AS flagObrigatorio,A.ID_QUESTIONARIO AS idQuestionario, A.ST_ATIVO AS ativo FROM CAD_PERGUNTA AS A  "
			+ "INNER JOIN CAD_QUESTIONARIO AS B ON B.ID = A.ID_QUESTIONARIO "
			+ "INNER JOIN CAD_TIPO_SERVICO AS C ON C.ID = B.ID_TIPO_SERVICO "
			+ "WHERE C.ID_CONTRATO IN( "
			+ "	SELECT E.ID_CONTRATO FROM USUARIO_REGIONAL AS D INNER JOIN CAD_REGIONAL AS E ON E.ID = D.ID_REGIONAL "
			+ "	WHERE D.ID_USUARIO = ?1 "
			+ ") ",nativeQuery = true)
	public List<PerguntaMobileProjection> getPerguntaMobile(Long idUsuario);
}
