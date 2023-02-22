package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Colaborador;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.ColaboradorRepository;
import br.com.sintec.repository.filter.ColaboradorFilter;
import br.com.sintec.repository.projection.ColaboradorProjection;
import br.com.sintec.util.Apoio;

@Service
public class ColaboradorService  implements IGenericService<Colaborador, ColaboradorFilter, Serializable>{

	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	
	@Override
	public Colaborador incluir(Colaborador entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Colaborador ColaboradorSalvo = this.colaboradorRepository.save(entity);
		return ColaboradorSalvo;
	}

	@Override
	public Colaborador alterar(Colaborador entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Colaborador ColaboradorAlterado = this.colaboradorRepository.save(entity);
		return ColaboradorAlterado;
	}

	@Override
	public Colaborador buscarPorId(long id) {
		Optional<Colaborador> ColaboradorEncontrado = this.colaboradorRepository.findById(id);
		return ColaboradorEncontrado.get();
	}

	@Override
	public List<Colaborador> pesquisar(ColaboradorFilter filter) {
		return this.colaboradorRepository.pesquisar(filter);
	}
	
	public List<ColaboradorProjection> listaColaboradorSemVeiculo(Long idRegional){
		return this.colaboradorRepository.listaColaboradorSemVeiculo(idRegional);
	}
}
