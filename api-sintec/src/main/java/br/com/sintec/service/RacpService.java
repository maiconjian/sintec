package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Racp;
import br.com.sintec.repository.RacpRepository;
import br.com.sintec.repository.filter.RacpFilter;
@Service
public class RacpService implements IGenericService<Racp, RacpFilter, Serializable> {

	@Autowired
	private RacpRepository racpRepository;
	
	
	@Override
	public Racp incluir(Racp entity) {
		Racp RacpSalvo = this.racpRepository.save(entity);
		return RacpSalvo;
	}

	@Override
	public Racp alterar(Racp entity) {
		Racp RacpAlterado = this.racpRepository.save(entity);
		return RacpAlterado;
	}

	@Override
	public Racp buscarPorId(long id) {
		Optional<Racp> RacpEncontrado = this.racpRepository.findById(id);
		return RacpEncontrado.get();
	}

	@Override
	public List<Racp> pesquisar(RacpFilter filter) {
		return this.racpRepository.pesquisar(filter);
	}
	
	
	public Racp buscarRacp(Long idServico) {
		return this.racpRepository.buscarRacp(idServico);
	}
	
}
