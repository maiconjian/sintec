package br.com.sintec.repository.impl.regional;

import java.util.List;

import br.com.sintec.model.Regional;
import br.com.sintec.repository.filter.RegionalFilter;

public interface RegionalRepositoryQuery {
	
	List<Regional> pesquisar(RegionalFilter filter);
	

}
