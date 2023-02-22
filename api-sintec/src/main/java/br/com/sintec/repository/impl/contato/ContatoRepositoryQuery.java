package br.com.sintec.repository.impl.contato;

import java.util.List;

import br.com.sintec.model.Contato;
import br.com.sintec.repository.filter.ContatoFilter;

public interface ContatoRepositoryQuery {

	public List<Contato> pesquisar(ContatoFilter filter);
}
