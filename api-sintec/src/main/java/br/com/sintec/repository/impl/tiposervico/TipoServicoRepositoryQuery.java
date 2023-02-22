package br.com.sintec.repository.impl.tiposervico;

import java.util.List;

import br.com.sintec.model.TipoServico;
import br.com.sintec.repository.filter.TipoServicoFilter;

public interface TipoServicoRepositoryQuery {
	
	public List<TipoServico> pesquisar(TipoServicoFilter filter);

}
