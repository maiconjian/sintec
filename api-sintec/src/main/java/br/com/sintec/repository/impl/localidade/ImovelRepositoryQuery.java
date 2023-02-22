package br.com.sintec.repository.impl.localidade;

import java.util.List;

import br.com.sintec.model.Imovel;
import br.com.sintec.repository.filter.ImovelFilter;

public interface ImovelRepositoryQuery {

	List<Imovel> pesquisar(ImovelFilter filter);
}
