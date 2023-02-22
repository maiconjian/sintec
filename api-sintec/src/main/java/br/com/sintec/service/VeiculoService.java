package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Veiculo;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.VeiculoRepository;
import br.com.sintec.repository.filter.VeiculoFilter;
import br.com.sintec.util.Apoio;

@Service
public class VeiculoService implements IGenericService<Veiculo, VeiculoFilter, Serializable>{

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	
	@Override
	public Veiculo incluir(Veiculo entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Veiculo VeiculoSalvo = this.veiculoRepository.save(entity);
		return VeiculoSalvo;
	}

	@Override
	public Veiculo alterar(Veiculo entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Veiculo VeiculoAlterado = this.veiculoRepository.save(entity);
		return VeiculoAlterado;
	}

	@Override
	public Veiculo buscarPorId(long id) {
		Optional<Veiculo> VeiculoEncontrado = this.veiculoRepository.findById(id);
		return VeiculoEncontrado.get();
	}

	@Override
	public List<Veiculo> pesquisar(VeiculoFilter filter) {
		return this.veiculoRepository.pesquisar(filter);
	}

}
