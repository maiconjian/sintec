package br.com.sintec.repository.projection;

public interface RetornoProjection {
	
	Long getIdServico();
	Long getCodigoServico();
	Long getIdUsuario();
	String getNomeUsuario();
	Long getIdColaborador();
	String getNomeColaborador();
	Long getIdVeiculo();
	String getPlaca();
	Long getIdImovel();
	String getNomeImovel();
	Long getIdOcorrencia();
	String getNomeOcorrencia();
	Long getIdRacp();
	Long getCodigoRacp();
	String getLatitude();
	String getLongitude();
	String getDataSolicitacao();
	String getDataProgramada();
	String getDataExecucao();

}
