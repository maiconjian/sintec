package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Contato;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.ContatoRepository;
import br.com.sintec.repository.filter.ContatoFilter;
import br.com.sintec.util.Apoio;

@Service
public class ContatoService implements IGenericService<Contato, ContatoFilter, Serializable>{

	
	@Autowired
	private ContatoRepository contatoRepository;
	
	
	@Override
	public Contato incluir(Contato entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Contato ContatoSalvo = this.contatoRepository.save(entity);
		return ContatoSalvo;
	}

	@Override
	public Contato alterar(Contato entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Contato ContatoAlterado = this.contatoRepository.save(entity);
		return ContatoAlterado;
	}

	@Override
	public Contato buscarPorId(long id) {
		Optional<Contato> ContatoEncontrado = this.contatoRepository.findById(id);
		return ContatoEncontrado.get();
	}

	@Override
	public List<Contato> pesquisar(ContatoFilter filter) {
		return this.contatoRepository.pesquisar(filter);
	}
	
	public void deletar(Long id) {
		this.contatoRepository.deletar(id);
	}
}
