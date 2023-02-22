package br.com.sintec.repository.projection;

public interface PerguntaMobileProjection {
	
	Long getId();
	String getEnunciado();
	Integer getOrdemApresentacao();
	Integer getFlagFoto();
	Integer getFlagMultiplaEscolha();
	Integer getFlagObrigatorio();
	Long getIdQuestionario();
	Integer getAtivo();

}
