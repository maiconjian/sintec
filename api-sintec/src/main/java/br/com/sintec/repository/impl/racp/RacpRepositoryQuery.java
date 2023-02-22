package br.com.sintec.repository.impl.racp;

import java.util.List;

import br.com.sintec.model.Racp;
import br.com.sintec.repository.filter.RacpFilter;

public interface RacpRepositoryQuery {
	

	List<Racp> pesquisar(RacpFilter filter);

}
