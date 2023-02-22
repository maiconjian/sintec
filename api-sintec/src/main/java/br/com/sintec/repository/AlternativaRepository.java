package br.com.sintec.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Alternativa;
import br.com.sintec.repository.projection.AlternativaMobileProjection;

public interface AlternativaRepository extends CrudRepository<Alternativa, Long> {
	
	@Query(value="SELECT * FROM CAD_ALTERNATIVA WHERE ID_PERGUNTA = ?1 "
				+"ORDER BY ST_ATIVO DESC ",nativeQuery = true)
	List<Alternativa> listaAlternativaPergunta(Long idPergunta);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE CAD_ALTERNATIVA SET DT_ATUALIZACAO =?1,ST_ATIVO = 0 WHERE ID_PERGUNTA = ?2 ",nativeQuery = true)
	public void desativarAlternativasPergunta(LocalDateTime dataAtualizacao,Long idPergunta);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE CAD_ALTERNATIVA SET  DT_ATUALIZACAO = ?1,ST_ATIVO = 0 WHERE ID_PERGUNTA IN (SELECT B.ID FROM CAD_PERGUNTA AS B WHERE B.ID_QUESTIONARIO = ?2) ",nativeQuery = true)
	public void desativarAlternativasPorQuestionario(LocalDateTime dataAtualizacao,Long idQuestionario);
	
	
	@Query(value="SELECT A.ID AS id, A.ID_PERGUNTA AS idPergunta,A.NM_DESCRICAO AS descricao,A.FLAG_NCONF AS flagNConf,A.NR_ORDEM_APRESENTACAO AS ordemApresentacao,A.ST_ATIVO AS ativo FROM CAD_ALTERNATIVA AS A  "
			+ "INNER JOIN CAD_PERGUNTA AS B ON B.ID = A.ID_PERGUNTA "
			+ "INNER JOIN CAD_QUESTIONARIO AS C ON C.ID = B.ID_QUESTIONARIO "
			+ "INNER JOIN CAD_TIPO_SERVICO AS D ON D.ID = C.ID_TIPO_SERVICO "
			+ "WHERE D.ID_CONTRATO IN ( "
			+ "		SELECT F.ID_CONTRATO FROM USUARIO_REGIONAL AS E  "
			+ "		INNER JOIN CAD_REGIONAL AS F ON F.ID = E.ID_REGIONAL "
			+ "		WHERE E.ID_USUARIO = ?1 "
			+ ") " ,nativeQuery = true)
	public List<AlternativaMobileProjection> getAlternativaMobile(Long idUsuario);

}
