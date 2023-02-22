package br.com.sintec.repository.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RacpFilter {
	
	private String codigoServico;
	
	private String codigoRacp;
	
    private String dataRef;
    
    private Long idSituacaoRacp;
    
    private Long idRegional;
    
    private Long idTipoServico;
    
}
