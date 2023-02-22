package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Alternativa;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.AlternativaRepository;
import br.com.sintec.repository.filter.AlternativaFilter;
import br.com.sintec.repository.projection.AlternativaMobileProjection;
import br.com.sintec.util.Apoio;

@Service
public class AlternativaService implements IGenericService<Alternativa, AlternativaFilter, Serializable> {

	@Autowired
	private AlternativaRepository alternativaRepository;
	
	
	@Override
	public Alternativa incluir(Alternativa entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Alternativa AlternativaSalvo = this.alternativaRepository.save(entity);
		return AlternativaSalvo;
	}

	@Override
	public Alternativa alterar(Alternativa entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Alternativa AlternativaAlterado = this.alternativaRepository.save(entity);
		return AlternativaAlterado;
	}

	@Override
	public Alternativa buscarPorId(long id) {
		Optional<Alternativa> AlternativaEncontrado = this.alternativaRepository.findById(id);
		return AlternativaEncontrado.get();
	}

	@Override
	public List<Alternativa> pesquisar(AlternativaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Alternativa> listaAlternativaPergunta(Long idPergunta){
		return this.alternativaRepository.listaAlternativaPergunta(idPergunta);
	}
	
	public void desativarAlternativasPergunta(Long idPergunta) {
		this.alternativaRepository.desativarAlternativasPergunta(Apoio.getDateTimeFuso(), idPergunta);
	}
	
	public void desativarAlternativasPorQuestionario(Long idQuestionario) {
		this.alternativaRepository.desativarAlternativasPorQuestionario(Apoio.getDateTimeFuso(), idQuestionario);
	}

	
	public List<AlternativaMobileProjection> getAlternativaMobile(Long idUsuario){
		return this.alternativaRepository.getAlternativaMobile(idUsuario);
	}
}
