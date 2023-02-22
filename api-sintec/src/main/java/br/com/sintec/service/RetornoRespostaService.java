package br.com.sintec.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.exception.CustomRuntimeException;
import br.com.sintec.model.Pergunta;
import br.com.sintec.model.RacpNConf;
import br.com.sintec.model.RetornoResposta;
import br.com.sintec.model.Servico;
import br.com.sintec.model.dto.RetornoRespostaDto;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.RetornoRespostaRepository;
import br.com.sintec.repository.filter.RetornoRespostaFilter;
import br.com.sintec.repository.projection.RetornoRespostaProjection;
import br.com.sintec.util.Apoio;
@Service
public class RetornoRespostaService implements IGenericService<RetornoResposta, RetornoRespostaFilter, Serializable>{

	
	@Autowired
	private RetornoRespostaRepository retornoRespostaRepository;
	@Autowired
	private RacpService racpService;
	@Autowired
	private RacpNConfService racpNConfService;
	@Autowired
	private AlternativaService alternativaService;

	
	
	@Override
	public RetornoResposta incluir(RetornoResposta entity) {
		RetornoResposta RetornoRespostaSalvo = this.retornoRespostaRepository.save(entity);
		return RetornoRespostaSalvo;
	}

	@Override
	public RetornoResposta alterar(RetornoResposta entity) {
		RetornoResposta RetornoRespostaAlterado = this.retornoRespostaRepository.save(entity);
		return RetornoRespostaAlterado;
	}

	@Override
	public RetornoResposta buscarPorId(long id) {
		Optional<RetornoResposta> RetornoRespostaEncontrado = this.retornoRespostaRepository.findById(id);
		return RetornoRespostaEncontrado.get();
	}

	@Override
	public List<RetornoResposta> pesquisar(RetornoRespostaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<Long> inlcuir(List<RetornoRespostaDto> listaDto){
		List<Long> listaIdServico = new ArrayList<>(); 
		
		try {
			for (RetornoRespostaDto retornoMobile : listaDto) {
				Optional<RetornoResposta> retornoRespostaEncontrado = this.retornoRespostaRepository.buscarRetornoResposta(retornoMobile.getIdServico(), retornoMobile.getIdPergunta());
				
				if(!retornoRespostaEncontrado.isPresent()) {
					RetornoResposta retornoSalvar = new RetornoResposta();
					retornoSalvar.setServico(new Servico(retornoMobile.getIdServico()));
					retornoSalvar.setPergunta(new Pergunta(retornoMobile.getIdPergunta()));
					retornoSalvar.setLatitude(retornoMobile.getLatitude());
					retornoSalvar.setLongitude(retornoMobile.getLongitude());
					retornoSalvar.setResposta(retornoMobile.getResposta());
					retornoSalvar.setDataExecucao(retornoMobile.getDataExecucao());
					
					if(retornoMobile.getIdAlternativa() != null) {
						retornoSalvar.setAlternativa(this.alternativaService.buscarPorId(retornoMobile.getIdAlternativa()));
					}
					
					RetornoResposta retorno = this.retornoRespostaRepository.save(retornoSalvar);
				
					this.incluirNConf(retorno);
					
					listaIdServico.add(retorno.getServico().getId());
				}
			}
			return listaIdServico;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomRuntimeException("Erro ao Sincronizar!");
		}
	}
	
	private void incluirNConf(RetornoResposta retornoResposta) {
		if(retornoResposta.getAlternativa() != null) {
			int isNconf = 1;
			int isMultiplaEscolha = 1;
			if(retornoResposta.getAlternativa().getFlagNconf() == isNconf && retornoResposta.getAlternativa().getPergunta().getFlagMultiplaEscolha() == isMultiplaEscolha) {
				try {
					RacpNConf nConf = new RacpNConf();
					nConf.setRacp(this.racpService.buscarRacp(retornoResposta.getServico().getId()));
					nConf.setRetornoResposta(retornoResposta);
					nConf.setDataAtualizacao(Apoio.getDateTimeFuso());
					nConf.setAtivo(SituacaoEnum.ATIVO.getValue());
					this.racpNConfService.incluir(nConf);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					throw new CustomRuntimeException("Erro ao salvar nao conformidade!");
				}
				
			}
		}
		

	}
	
	public List<RetornoRespostaProjection> getRespostas(Long idServico){
		return this.retornoRespostaRepository.getRespostas(idServico);
	}
	
}
