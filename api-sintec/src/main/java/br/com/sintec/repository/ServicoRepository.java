package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Servico;
import br.com.sintec.repository.projection.ServicoLiberadoMobileProjection;
import br.com.sintec.repository.projection.ServicoPendenteProjection;

public interface ServicoRepository extends CrudRepository<Servico, Long>{
	
	@Query(value = "SELECT A.ID AS id,A.CD_SERVICO AS codigo,A.DT_REF as dataRef,A.DT_SOLICITACAO AS dataSolicitacao, "
			+ "A.DT_PROGRAMADA AS dataProgramada,B.NM_NOME AS nomeColaborador,E.NM_PLACA AS placa,F.NM_NOME AS nomeImovel,A.ST_ATIVO AS ativo FROM CAD_SERVICO AS A "
			+ "LEFT JOIN CAD_COLABORADOR AS B ON B.ID = A.ID_COLABORADOR "
			+ "LEFT OUTER JOIN SERV_DISTRIBUICAO AS C ON C.ID_SERVICO = A.ID "
			+ "LEFT OUTER JOIN SERV_RETORNO AS D ON D.ID_SERVICO = A.ID "
			+ "LEFT OUTER JOIN CAD_VEICULO AS E ON E.ID = A.ID_VEICULO "
			+ "LEFT OUTER JOIN CAD_IMOVEL AS F ON F.ID = A.ID_IMOVEL "
			+ "WHERE C.ID IS NULL AND D.ID IS NULL "
			+ "AND A.ID_REGIONAL = ?1 "
			+ "AND (A.ID_TIPO_SERVICO = ?2 OR ?2 = 0) "
			+ "AND A.DT_REF = ?3 "
			+ "AND A.ST_ATIVO = ?4 "
			+ "ORDER BY A.ID DESC ", nativeQuery = true)
	public List<ServicoPendenteProjection> listaServicoPendente(Long idRegional,
			Long idTipoServico, String dataRef,int ativo);
	
	
	@Query(value = "SELECT A.ID AS id,A.CD_SERVICO AS codigo, A.DT_REF AS dataRef,D.ID_CONTRATO AS idContrato,D.ID AS idRegional,D.NM_NOME as nomeRegional,F.ID as idCategoriaTipoServico,F.NM_NOME as nomeCategoriaTipoServico,  "
			+ "			 E.ID as idTipoServico,E.NM_NOME as nomeTipoServico,G.ID AS idImovel,G.END_BAIRRO as bairro,G.END_LOGRADOURO as logradouro,G.END_NUMERO as numeroLogradouro,  "
			+ "			 H.ID AS idVeiculo,H.NM_PLACA as placa,H.NM_COR as cor,H.NM_MODELO as modelo,I.ID as idColaborador,I.NM_NOME as nomeColaborador,I.NR_CNH as cnh,  "
			+ "			 I.DT_VALIDADE_CNH as validadeCnh,I.DS_CATEGORIA_CNH as categoriaCnh,A.DT_PROGRAMADA as dataProgramada,b.ID_USUARIO as idUsuario  "
			+ "			 FROM CAD_SERVICO AS A  "
			+ "			 INNER JOIN SERV_DISTRIBUICAO AS B ON B.ID_SERVICO = A.ID  "
			+ "			 INNER JOIN SYS_USUARIO AS C ON C.ID = B.ID_USUARIO "
			+ "			 INNER JOIN CAD_REGIONAL AS D ON D.ID = A.ID_REGIONAL  "
			+ "			 INNER JOIN CAD_TIPO_SERVICO AS E ON E.ID = A.ID_TIPO_SERVICO  "
			+ "			 INNER JOIN CAD_CATEGORIA_TIPO_SERVICO AS F ON F.ID = E.ID_CATEGORIA_TIPO_SERVICO  "
			+ "			 LEFT OUTER JOIN CAD_IMOVEL AS G ON G.ID = A.ID_IMOVEL  "
			+ "			 LEFT OUTER JOIN CAD_VEICULO AS H ON H.ID = A.ID_VEICULO  "
			+ "			 LEFT OUTER JOIN CAD_COLABORADOR AS I ON I.ID = A.ID_COLABORADOR  "
			+ "			 WHERE B.ID_USUARIO = ?1 AND B.FLAG_ASSOCIADO = 1 AND C.ST_ATIVO = 1",nativeQuery = true)
	public List<ServicoLiberadoMobileProjection> getServicoLiberadoMobile(Long idUsuario);
}
