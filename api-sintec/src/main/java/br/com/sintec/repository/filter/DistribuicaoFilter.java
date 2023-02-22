package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistribuicaoFilter {
	
	private Long idRegional;
	
	private Long idLocalidade;
	
	private Long idTipoServico;
	
	private String dataRef;
	
	private int ativo;
	
	private Long idUsuario;
}
