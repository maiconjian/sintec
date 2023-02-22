package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Pergunta;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.PerguntaRepository;
import br.com.sintec.repository.filter.PerguntaFilter;
import br.com.sintec.repository.projection.PerguntaMobileProjection;
import br.com.sintec.util.Apoio;

@Service
public class PerguntaService implements IGenericService<Pergunta, PerguntaFilter, Serializable>{

	@Autowired
	private PerguntaRepository perguntaRepository;
	@Autowired
	private AlternativaService alternativaService;
	
	
	@Override
	public Pergunta incluir(Pergunta entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Pergunta PerguntaSalvo = this.perguntaRepository.save(entity);
		return PerguntaSalvo;
	}

	@Override
	public Pergunta alterar(Pergunta entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Pergunta PerguntaAlterado = this.perguntaRepository.save(entity);
		return PerguntaAlterado;
	}

	@Override
	public Pergunta buscarPorId(long id) {
		Optional<Pergunta> PerguntaEncontrado = this.perguntaRepository.findById(id);
		return PerguntaEncontrado.get();
	}

	@Override
	public List<Pergunta> pesquisar(PerguntaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Pergunta> listaPerguntaQuestionario(Long idPergunta){
		return this.perguntaRepository.listaPerguntaQuestionario(idPergunta);
	}
	
	public Pergunta alterarSituacaoPergunta(Pergunta entity) {
		if(entity.getAtivo() == 0) {
			this.alternativaService.desativarAlternativasPergunta(entity.getId());
		}
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		return this.perguntaRepository.save(entity);
	}
	
	public void desativarPerguntasQuestionario(Long idPergunta) {
		this.perguntaRepository.desativarPerguntasQuestionario(Apoio.getDateTimeFuso(), idPergunta);
	}
	
	
	public List<PerguntaMobileProjection> getPerguntaMobile(Long idUsuario){
		return this.perguntaRepository.getPerguntaMobile(idUsuario);
	}
}
