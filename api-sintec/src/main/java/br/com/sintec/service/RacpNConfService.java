package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.RacpNConf;
import br.com.sintec.repository.RacpNConfRepository;
import br.com.sintec.repository.filter.RacpNConfFilter;

@Service
public class RacpNConfService implements IGenericService<RacpNConf, RacpNConfFilter, Serializable> {

	@Autowired
	private RacpNConfRepository racpNConfRepository;
	
	
	@Override
	public RacpNConf incluir(RacpNConf entity) {
		RacpNConf RacpNConfSalvo = this.racpNConfRepository.save(entity);
		return RacpNConfSalvo;
	}

	@Override
	public RacpNConf alterar(RacpNConf entity) {
		RacpNConf RacpNConfAlterado = this.racpNConfRepository.save(entity);
		return RacpNConfAlterado;
	}

	@Override
	public RacpNConf buscarPorId(long id) {
		Optional<RacpNConf> RacpNConfEncontrado = this.racpNConfRepository.findById(id);
		return RacpNConfEncontrado.get();
	}

	@Override
	public List<RacpNConf> pesquisar(RacpNConfFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
