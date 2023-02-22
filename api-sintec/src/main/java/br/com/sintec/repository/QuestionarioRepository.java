package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Questionario;
import br.com.sintec.repository.impl.questionario.QuestionarioRepositoryQuery;
import br.com.sintec.repository.projection.QuestionarioMobileProjection;

public interface QuestionarioRepository extends CrudRepository<Questionario, Long> ,QuestionarioRepositoryQuery{
	
	
	@Query(value="SELECT A.ID AS id, A.NM_TITULO AS titulo,A.NR_ORDEM_APRESENTACAO AS ordemApresentacao,A.ID_TIPO_SERVICO AS idTipoServico,A.ST_ATIVO AS ativo    "
			+ "			 FROM CAD_QUESTIONARIO AS A   "
			+ "			 INNER JOIN CAD_TIPO_SERVICO AS B ON B.ID = A.ID_TIPO_SERVICO  "
			+ "			 WHERE B.ID_CONTRATO IN (  "
			+ "			 SELECT D.ID_CONTRATO FROM USUARIO_REGIONAL AS C "
			+ "				INNER JOIN CAD_REGIONAL AS D ON D.ID = C.ID_REGIONAL "
			+ "			 	WHERE C.ID_USUARIO = ?1 "
			+ "			) ",nativeQuery = true)
	public List<QuestionarioMobileProjection> getQuestionarioMobile(Long idUsuario);

}
