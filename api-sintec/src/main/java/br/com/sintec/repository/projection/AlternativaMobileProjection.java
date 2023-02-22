package br.com.sintec.repository.projection;

public interface AlternativaMobileProjection {

	Long getId();
	Long getIdPergunta();
	String getDescricao();
	Integer getFlagNConf();
	Integer getOrdemApresentacao();
	Integer getAtivo();
	
}
