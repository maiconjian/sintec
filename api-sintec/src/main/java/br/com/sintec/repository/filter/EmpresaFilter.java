package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaFilter {

	private String nomeFantasia;
	
	private String cnpj;
	
	private int ativo;
}
