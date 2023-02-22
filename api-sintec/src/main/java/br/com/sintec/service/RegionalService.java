package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Regional;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.RegionalRepository;
import br.com.sintec.repository.filter.RegionalFilter;
import br.com.sintec.repository.projection.RegionalUsuarioProjection;
import br.com.sintec.util.Apoio;

@Service
public class RegionalService implements IGenericService<Regional, RegionalFilter, Serializable>{

	@Autowired
	private RegionalRepository regionalRepository;
	
	
	@Override
	public Regional incluir(Regional entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Regional RegionalSalvo = this.regionalRepository.save(entity);
		return RegionalSalvo;
	}

	@Override
	public Regional alterar(Regional entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Regional RegionalAlterado = this.regionalRepository.save(entity);
		return RegionalAlterado;
	}

	@Override
	public Regional buscarPorId(long id) {
		Optional<Regional> RegionalEncontrado = this.regionalRepository.findById(id);
		return RegionalEncontrado.get();
	}

	@Override
	public List<Regional> pesquisar(RegionalFilter filter) {
		return this.regionalRepository.pesquisar(filter);
	}
	
	public List<RegionalUsuarioProjection> listaRegionalUsuario(Long idUsuario){
		return this.regionalRepository.listaRegionalUsuario(idUsuario);
	}
}
