package br.com.sintec.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Distribuicao;
import br.com.sintec.repository.projection.DistribuidoProjection;
import br.com.sintec.repository.projection.ServicoDistribuidoProjection;

public interface DistribuicaoRepository extends CrudRepository<Distribuicao, Long> {


	@Query(value = "SELECT B.ID AS id,B.DT_PROGRAMADA AS dataProgramada,B.CD_SERVICO as codigo, "
			+ "			 C.NM_NOME AS nomeColaborador,A.FLAG_ASSOCIADO AS flagAssociado FROM SERV_DISTRIBUICAO AS A "
			+ "			 INNER JOIN CAD_SERVICO AS B ON B.ID = A.ID_SERVICO "
			+ "			 INNER JOIN CAD_COLABORADOR AS C ON C.ID = B.ID_COLABORADOR "
			+ "			 LEFT OUTER JOIN SERV_RETORNO AS D ON D.ID_SERVICO = A.ID_SERVICO  "
			+ "			 WHERE  B.ID_REGIONAL = ?1 "
			+ "			 AND (B.ID_TIPO_SERVICO = ?2 OR ?2 = 0)  "
			+ "			 AND B.DT_REF = ?3 "
			+ "			 AND A.ID_USUARIO = ?4 "
			+ "			 AND B.ST_ATIVO = 1 "
			+ "			 AND D.ID IS NULL ", nativeQuery = true)
	public List<DistribuidoProjection> listaDistribuidoUsuario(Long idRegional,
			Long idTipoServico, String dataRef, Long idUsuario);

	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO SERV_DESASSOCIADO(ID_SERVICO,ID_USUARIO) "
				 + "SELECT A.ID_SERVICO,A.ID_USUARIO FROM SERV_DISTRIBUICAO AS A WHERE A.ID_SERVICO = ?1 AND A.ID_USUARIO =?2 AND A.FLAG_ASSOCIADO = 2 ", nativeQuery = true)
	public void incluirDesassociado(Long idServico,Long idUsuario);

	@Query(value = "DELETE A FROM SERV_DISTRIBUICAO AS A "
			+ "LEFT OUTER JOIN SERV_RETORNO AS B ON B.ID_SERVICO = A.ID_SERVICO "
			+ "WHERE A.ID_SERVICO = ?1 AND A.ID_USUARIO = ?2 AND B.ID IS NULL "
			+ "SELECT @@ROWCOUNT ", nativeQuery = true)
	public Integer deletarDistribuicao(Long idServico, Long idUsuario);
	
	@Query(value="SELECT A.ID_SERVICO FROM SERV_DESASSOCIADO AS A WHERE A.ID_USUARIO = ?1 ",nativeQuery = true)
	public List<Long> getDesassociadoMobile(Long idUsuario);
	
	@Modifying
	@Transactional
	@Query(value="DELETE SERV_DESASSOCIADO WHERE ID_SERVICO IN(?1) ",nativeQuery = true)
	public void deletarDesassociados(List<Long> listaIdServico);
	

	@Query(value="SELECT  A.ID AS idDistribuicao,"
			+ "B.DT_SOLICITACAO AS dataSolicitacao,  "
			+ "B.DT_PROGRAMADA AS dataProgramada, "
			+ "B.CD_SERVICO as codigo,  "
			+ "C.NM_NOME AS nomeColaborador, "
			+ "D.NM_NOME AS nomeUsuario, "
			+ "A.FLAG_ASSOCIADO AS flagAsossiado "
			+ "FROM SERV_DISTRIBUICAO AS A  "
			+ "INNER JOIN CAD_SERVICO AS B ON B.ID = A.ID_SERVICO  "
			+ "INNER JOIN CAD_COLABORADOR AS C ON C.ID = B.ID_COLABORADOR  "
			+ "INNER JOIN SYS_USUARIO AS D ON D.ID = A.ID_USUARIO "
			+ "LEFT OUTER JOIN SERV_RETORNO AS E ON E.ID_SERVICO = A.ID_SERVICO   "
			+ "WHERE  B.ID_REGIONAL = ?1  "
			+ "AND (B.ID_TIPO_SERVICO = ?2 OR ?2 = 0)   "
			+ "AND B.DT_REF = ?3 "
			+ "AND (A.ID_USUARIO = ?4 OR ?4 = 0)  "
			+ "AND B.ST_ATIVO = 1 "
			+ "AND E.ID IS NULL ",nativeQuery = true)
	 List<ServicoDistribuidoProjection> listaDistribuido(Long idRegional,
			Long idTipoServico, String dataRef, Long idUsuario);

	@Transactional
	@Modifying
	@Query(value="UPDATE SERV_DISTRIBUICAO SET FLAG_ASSOCIADO = 1 ,DT_ATUALIZACAO = ?2 WHERE ID IN (?1) "
		        +"SELECT @@ROWCOUNT  ",nativeQuery = true)
	public Integer liberarServico(List<Long> listaIdDistribuicao,LocalDateTime dataAtualizacao);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE SERV_DISTRIBUICAO  SET FLAG_ASSOCIADO = 0 WHERE ID = ?1 AND FLAG_ASSOCIADO = 1 "
				+"SELECT @@ROWCOUNT ",nativeQuery = true)
	public Integer cancelarLiberacao(Long idDistribuicao);
	
//	mobile
	@Query(value="UPDATE SERV_DISTRIBUICAO SET FLAG_ASSOCIADO = 2 WHERE ID_SERVICO IN(?1) AND ID_USUARIO = ?2 "+
			 "SELECT @@ROWCOUNT ",nativeQuery = true)
	public Integer confirmarDownloadServico(List<Long> idServico,Long idUsuario);
	

}
