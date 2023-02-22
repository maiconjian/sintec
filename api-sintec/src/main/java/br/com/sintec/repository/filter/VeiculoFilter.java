package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoFilter {
	
	private Long idRegional;
	
	private String placa;
	
	private Integer idCategoriaVeiculo;
	
	private Integer ativo;

}
