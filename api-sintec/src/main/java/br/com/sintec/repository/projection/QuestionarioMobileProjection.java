package br.com.sintec.repository.projection;

public interface QuestionarioMobileProjection {
	
	Long getId();
	String getTitulo();
	Integer getOrdemApresentacao();
	Long getIdTipoServico();
	Integer getAtivo();

}
