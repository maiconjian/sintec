package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Contrato;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.ContratoRepository;
import br.com.sintec.repository.filter.ContratoFilter;
import br.com.sintec.repository.projection.ContratoUsuarioProjection;
import br.com.sintec.util.Apoio;

@Service
public class ContratoService implements IGenericService<Contrato, ContratoFilter, Serializable> {

	@Autowired
	private ContratoRepository contratoRepository;
	
	
	@Override
	public Contrato incluir(Contrato entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		return this.contratoRepository.save(entity);
	}

	@Override
	public Contrato alterar(Contrato entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Contrato ContratoAlterado = this.contratoRepository.save(entity);
		return ContratoAlterado;
	}

	@Override
	public Contrato buscarPorId(long id) {
		Optional<Contrato> ContratoEncontrado = this.contratoRepository.findById(id);
		return ContratoEncontrado.get();
	}

	@Override
	public List<Contrato> pesquisar(ContratoFilter filter) {
		return this.contratoRepository.pesquisar(filter);
	}
	
	public List<ContratoUsuarioProjection> listaContratoUsuario(Long idUsuario){
		return this.contratoRepository.listaContratoUsuario(idUsuario);
	}
}
