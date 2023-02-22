package br.com.sintec.repository.impl.colaborador;

import java.util.List;

import br.com.sintec.model.Colaborador;
import br.com.sintec.repository.filter.ColaboradorFilter;

public interface ColaboradorRepositoryQuery {

	public List<Colaborador> pesquisar(ColaboradorFilter filter);
}
