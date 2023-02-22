package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.CategoriaTipoServico;
import br.com.sintec.repository.CategoriaTipoServicoRepository;
import br.com.sintec.repository.filter.CategoriaTipoServicoFilter;

@Service
public class CategoriaTipoServicoService implements IGenericService<CategoriaTipoServico, CategoriaTipoServicoFilter, Serializable>{
	
	@Autowired
	private CategoriaTipoServicoRepository repository;
	
	@Override
	public CategoriaTipoServico incluir(CategoriaTipoServico entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaTipoServico alterar(CategoriaTipoServico entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaTipoServico buscarPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoriaTipoServico> pesquisar(CategoriaTipoServicoFilter filter) {
		return this.repository.pesquisar(filter.getAtivo());
	}

}
