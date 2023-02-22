package br.com.sintec.repository.impl.veiculo;

import java.util.List;

import br.com.sintec.model.Veiculo;
import br.com.sintec.repository.filter.VeiculoFilter;

public interface VeiculoRepositoryQuery {

	public List<Veiculo> pesquisar(VeiculoFilter filter);
}
