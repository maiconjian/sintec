package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.SituacaoRacp;
import br.com.sintec.repository.SituacaoRacpRepository;
import br.com.sintec.repository.filter.SituacaoRacpFilter;

@Service
public class SituacaoRacpService implements IGenericService<SituacaoRacp,SituacaoRacpFilter, Serializable> {
	
	@Autowired
	private SituacaoRacpRepository repository;

	@Override
	public SituacaoRacp incluir(SituacaoRacp entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SituacaoRacp alterar(SituacaoRacp entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SituacaoRacp buscarPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SituacaoRacp> pesquisar(SituacaoRacpFilter filter) {
		return this.repository.pesquisar(filter);
	}
	

}
