package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Questionario;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.QuestionarioRepository;
import br.com.sintec.repository.filter.QuestionarioFilter;
import br.com.sintec.repository.projection.QuestionarioMobileProjection;
import br.com.sintec.util.Apoio;

@Service
public class QuestionarioService implements IGenericService<Questionario, QuestionarioFilter, Serializable>{


	@Autowired
	private QuestionarioRepository questionarioRepository;
	
	@Autowired
	private PerguntaService perguntaService;
	@Autowired
	private AlternativaService alternativaService;
	
	
	@Override
	public Questionario incluir(Questionario entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		return this.questionarioRepository.save(entity);
	}

	@Override
	public Questionario alterar(Questionario entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Questionario QuestionarioAlterado = this.questionarioRepository.save(entity);
		return QuestionarioAlterado;
	}

	@Override
	public Questionario buscarPorId(long id) {
		Optional<Questionario> QuestionarioEncontrado = this.questionarioRepository.findById(id);
		return QuestionarioEncontrado.get();
	}

	@Override
	public List<Questionario> pesquisar(QuestionarioFilter filter) {
		return this.questionarioRepository.pesquisar(filter);
	}
	
	public Questionario alterarSituacaoQuestionario(Questionario entity) {
		if(entity.getAtivo() == 0) {
			this.perguntaService.desativarPerguntasQuestionario(entity.getId());
			this.alternativaService.desativarAlternativasPorQuestionario(entity.getId());
		}
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		return this.questionarioRepository.save(entity);
	}
	
	public List<QuestionarioMobileProjection> getQuestionarioMobile(Long idUsuario){
		return  this.questionarioRepository.getQuestionarioMobile(idUsuario);
	}
	
}
