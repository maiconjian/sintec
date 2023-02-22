package br.com.sintec.repository.projection;

public interface DistribuidoProjection {
	
	Long getId();
	Long getCodigo();
	String getNomeColaborador();
	String getDataRef();
	String getDataSolicitacao();
	String getDataProgramada();
    Integer getFlagAssociado();
	Integer getAtivo();
	

}
