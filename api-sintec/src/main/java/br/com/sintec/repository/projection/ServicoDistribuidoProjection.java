package br.com.sintec.repository.projection;

public interface ServicoDistribuidoProjection {

	
	 Long getIdDistribuicao();
	
	 String getNomeLocalidade();
	
	 String getDataSolicitacao();
	
	 String getDataProgramada();
	
	 Long getCodigo();
	
	 String getNomeColaborador();
	 
	 String getNomeUsuario();
	 
	 int getFlagAsossiado();
}
