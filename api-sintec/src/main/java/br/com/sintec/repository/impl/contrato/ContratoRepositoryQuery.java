package br.com.sintec.repository.impl.contrato;

import java.util.List;

import br.com.sintec.model.Contrato;
import br.com.sintec.repository.filter.ContratoFilter;

public interface ContratoRepositoryQuery {
	
	List<Contrato> pesquisar(ContratoFilter filter);

}
