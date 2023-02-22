package br.com.sintec.repository.projection;

public interface ServicoPendenteProjection {
	
	Long getId();
	Long getCodigo();
	String getNomeColaborador();
	String getPlaca();
	String getNomeImovel();
	String getDataRef();
	String getDataSolicitacao();
	String getDataProgramada();
	Integer getAtivo();
	

}
