package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionarioFilter {
	
	private Long idContrato;
	
	private Long idTipoServico;
	
	private int ativo;

}
