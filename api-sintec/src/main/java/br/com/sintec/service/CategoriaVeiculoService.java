package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.CategoriaVeiculo;
import br.com.sintec.repository.CategoriaVeiculoRepository;
import br.com.sintec.repository.filter.CategoriaVeiculoFilter;

@Service
public class CategoriaVeiculoService implements IGenericService<CategoriaVeiculo, CategoriaVeiculoFilter, Serializable> {

	@Autowired
	private CategoriaVeiculoRepository repository;
	
	@Override
	public CategoriaVeiculo incluir(CategoriaVeiculo entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaVeiculo alterar(CategoriaVeiculo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaVeiculo buscarPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoriaVeiculo> pesquisar(CategoriaVeiculoFilter filter) {
		return this.repository.pesquisar(filter.getAtivo());
	}

}
