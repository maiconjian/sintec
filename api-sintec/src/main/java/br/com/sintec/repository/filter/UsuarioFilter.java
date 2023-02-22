package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter {
	
	private String nome;
	
	private Long idPerfil;
	
	private Long idRegional;
	
	private Long ativo;

}
