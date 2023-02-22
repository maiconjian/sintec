package br.com.sintec.repository.projection;

public interface RetornoRespostaProjection {
	
	String getTituloQuestionario();
	String getEnunciado();
	String getResposta();
	String getPathFoto();
	Integer getFlagAssinatura();
	Integer getNconf();

}
