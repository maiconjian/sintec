package br.com.sintec.repository.projection;

public interface UsuarioMobileProjection {
	
	Long getId();
	String getNome();
	Long getMatricula();
	String getPin();
	int getAtivo();

}
