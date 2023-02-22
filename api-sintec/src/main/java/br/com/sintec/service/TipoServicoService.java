package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.TipoServico;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.TipoServicoRepository;
import br.com.sintec.repository.filter.TipoServicoFilter;
import br.com.sintec.util.Apoio;

@Service
public class TipoServicoService implements IGenericService<TipoServico,TipoServicoFilter, Serializable> {

	@Autowired
	private TipoServicoRepository tipoServicoRepository;
	
	
	@Override
	public TipoServico incluir(TipoServico entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		TipoServico TipoServicoSalvo = this.tipoServicoRepository.save(entity);
		return TipoServicoSalvo;
	}

	@Override
	public TipoServico alterar(TipoServico entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		TipoServico TipoServicoAlterado = this.tipoServicoRepository.save(entity);
		return TipoServicoAlterado;
	}

	@Override
	public TipoServico buscarPorId(long id) {
		Optional<TipoServico> TipoServicoEncontrado = this.tipoServicoRepository.findById(id);
		return TipoServicoEncontrado.get();
	}

	@Override
	public List<TipoServico> pesquisar(TipoServicoFilter filter) {
		return this.tipoServicoRepository.pesquisar(filter);
	}
	
	
	
}
