package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColaboradorFilter {

	private Long idRegional;
	
	private Long matricula;
	
	private String nome;
	
	private Integer ativo;
}
