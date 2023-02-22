package br.com.sintec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Retorno;
import br.com.sintec.repository.projection.RetornoProjection;

public interface RetornoRepository extends CrudRepository<Retorno, Long> {

	@Query(value="SELECT * FROM SERV_RETORNO WHERE ID_SERVICO = ?1 AND ST_ATIVO = 1",nativeQuery = true)
	public Optional<Retorno> buscarRetornoServico(Long idServico);
	
	@Query(value="SELECT B.ID AS idServico, B.CD_SERVICO AS codigoServico,C.ID AS idUsuario,C.NM_NOME as nomeUsuario,G.ID AS idColaborador,  "
			+ "			 G.NM_NOME AS nomeColaborador,F.ID AS idVeiculo, F.NM_PLACA AS placa,  "
			+ "			 E.ID as idImovel,E.NM_NOME AS nomeImovel,D.ID AS idOcorrencia,D.DS_DESCRICAO AS nomeOcorrencia,  "
			+ "			 H.ID AS idRacp, H.CD_RACP AS codigoRacp,A.CD_LATITUDE AS latitude,A.CD_LONGITUDE AS longitude,B.DT_SOLICITACAO AS dataSolicitacao,B.DT_PROGRAMADA AS dataProgramada,  "
			+ "			 A.DT_EXECUCAO AS dataExecucao FROM SERV_RETORNO AS A  "
			+ "			 INNER JOIN CAD_SERVICO AS B ON B.ID = A.ID_SERVICO  "
			+ "			 INNER JOIN SYS_USUARIO AS C ON C.ID = A.ID_USUARIO  "
			+ "			 LEFT OUTER JOIN CAD_OCORRENCIA AS D ON D.ID = A.ID_OCORRENCIA  "
			+ "			 LEFT OUTER JOIN CAD_IMOVEL AS E ON E.ID = B.ID_IMOVEL  "
			+ "			 LEFT OUTER JOIN CAD_VEICULO AS F ON F.ID = B.ID_VEICULO  "
			+ "			 LEFT OUTER JOIN CAD_COLABORADOR AS G ON G.ID = B.ID_COLABORADOR  "
			+ "			 LEFT OUTER JOIN CAD_RACP AS H ON H.ID_SERVICO = A.ID_SERVICO  "
			+ "			 WHERE B.DT_REF = ?1 "
			+ "			 AND B.ID_REGIONAL = ?2  "
			+ "			 AND (B.ID_TIPO_SERVICO = ?3 OR ?3 = 0)  "
			+ "			 AND (B.CD_SERVICO = ?4 OR ?4 = '')  "
			+ "			 AND A.ST_ATIVO = 1 ",nativeQuery = true)
	public List<RetornoProjection> pesquisar(String anoMesRef,Long idRegional,Long idTipoServico,Long codigoSerivco);
}
