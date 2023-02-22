package br.com.sintec.repository.impl.situacaoracp;

import java.util.List;

import br.com.sintec.model.SituacaoRacp;
import br.com.sintec.repository.filter.SituacaoRacpFilter;

public interface SituacaoRacpRepositoryQuery {
	
	public List<SituacaoRacp> pesquisar(SituacaoRacpFilter filter);

}
