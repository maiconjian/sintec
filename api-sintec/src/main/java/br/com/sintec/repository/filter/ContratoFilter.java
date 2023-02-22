package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratoFilter {

	private Long idEmpresa;
	
	private String nome;
	
	private Integer ativo;
}
