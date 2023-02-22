package br.com.sintec.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Veiculo;
import br.com.sintec.repository.impl.veiculo.VeiculoRepositoryQuery;

public interface VeiculoRepository extends CrudRepository<Veiculo, Long>,VeiculoRepositoryQuery{

}
