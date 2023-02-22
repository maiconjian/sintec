package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Imovel;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.ImovelRepository;
import br.com.sintec.repository.filter.ImovelFilter;
import br.com.sintec.util.Apoio;

@Service
public class ImovelService implements IGenericService<Imovel, ImovelFilter, Serializable>{

	@Autowired
	private ImovelRepository localidadeRepository;
	
	
	@Override
	public Imovel incluir(Imovel entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Imovel LocalidadeSalvo = this.localidadeRepository.save(entity);
		return LocalidadeSalvo;
	}

	@Override
	public Imovel alterar(Imovel entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Imovel LocalidadeAlterado = this.localidadeRepository.save(entity);
		return LocalidadeAlterado;
	}

	@Override
	public Imovel buscarPorId(long id) {
		Optional<Imovel> LocalidadeEncontrado = this.localidadeRepository.findById(id);
		return LocalidadeEncontrado.get();
	}

	@Override
	public List<Imovel> pesquisar(ImovelFilter filter) {
		return this.localidadeRepository.pesquisar(filter);
	}
	
	public List<Imovel> listaLocalidadePorRegional(Long idRegional){
		return this.localidadeRepository.listaLocalidadePorRegional(idRegional);
	}

}
