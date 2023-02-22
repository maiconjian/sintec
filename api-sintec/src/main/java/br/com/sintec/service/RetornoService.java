package br.com.sintec.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.exception.CustomRuntimeException;
import br.com.sintec.model.Racp;
import br.com.sintec.model.Retorno;
import br.com.sintec.model.Servico;
import br.com.sintec.model.SituacaoRacp;
import br.com.sintec.model.Usuario;
import br.com.sintec.model.dto.RetornoDto;
import br.com.sintec.model.helpers.SituacaoRacpEnum;
import br.com.sintec.repository.RetornoRepository;
import br.com.sintec.repository.filter.RetornoFilter;
import br.com.sintec.repository.projection.RetornoProjection;
import br.com.sintec.util.Apoio;

@Service
public class RetornoService implements IGenericService<Retorno, RetornoFilter, Serializable>{

	@Autowired
	private RetornoRepository retornoRepository;
//	@Autowired
//	private ServicoService servicoService;
//	@Autowired
//	private UsuarioService usuarioService;
	@Autowired
	private OcorrenciaService ocorrenciaService;
	@Autowired
	private RacpService racpService;
	
	
	@Override
	public Retorno incluir(Retorno entity) {
		Retorno RetornoSalvo = this.retornoRepository.save(entity);
		return RetornoSalvo;
	}
	
	public List<Long> incluir(List<RetornoDto> listaRetorno){
		List<Long> listaIdServico = new ArrayList<>(); 
		
		try {
			for (RetornoDto retorno : listaRetorno) {
				Optional<Retorno> retornoPesquisado = this.retornoRepository.buscarRetornoServico(retorno.getIdServico());
				if(!retornoPesquisado.isPresent()) {
					Retorno retornoMontado = new Retorno();
					retornoMontado.setServico(new Servico(retorno.getIdServico()));
					retornoMontado.setUsuario(new Usuario(retorno.getIdUsuario()));
					retornoMontado.setDataExecucao(retorno.getDataExecucao());
					retornoMontado.setModeloAparelho(retorno.getModeloAparelho());
					retornoMontado.setMarcaAparelho(retorno.getMarcaAparelho());
					retornoMontado.setFlagRacp(retorno.getFlagRacp());
					retornoMontado.setLongitude(retorno.getLongitude());
					retornoMontado.setLatitude(retorno.getLatitude());
					retornoMontado.setAtivo(retorno.getAtivo());
					
					if(retorno.getIdOcorrencia() != null && retorno.getIdOcorrencia() != 0) {
						retornoMontado.setOcorrencia(this.ocorrenciaService.buscarPorId(retorno.getIdOcorrencia()));
					}
					
					Retorno retornoSalvo = this.retornoRepository.save(retornoMontado);
					listaIdServico.add(retornoSalvo.getServico().getId());
					
					if(retornoSalvo.getFlagRacp() == 1) {
						gerarRacp(retornoSalvo);
					}
				}
			
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomRuntimeException("Erro ao Sincronizar!");
		}
		return listaIdServico;	
	}
	
	private Racp gerarRacp(Retorno retorno) {
		String mes = Apoio.getDateTimeFuso().getMonthValue()+"";
		String ano = Apoio.getDateTimeFuso().getYear()+"";
		String codigo = ano.substring(2,4)+mes+retorno.getId().toString();
			
		Racp racp = new Racp();
		racp.setServico(retorno.getServico());
		racp.setDataRef(LocalDate.parse(ano+"-"+mes+"-01"));
		racp.setDataProgramada(null);
		racp.setDataGeracao(Apoio.getDateTimeFuso().toLocalDate());
		racp.setCodigoRacp(codigo);
		racp.setDataAtualizacao(Apoio.getDateTimeFuso());
	    racp.setSituacao(new SituacaoRacp(SituacaoRacpEnum.PENDENTE.getValue()));
		return this.racpService.incluir(racp);	
	}
	
	@Override
	public Retorno alterar(Retorno entity) {
		Retorno RetornoAlterado = this.retornoRepository.save(entity);
		return RetornoAlterado;
	}

	@Override
	public Retorno buscarPorId(long id) {
		Optional<Retorno> RetornoEncontrado = this.retornoRepository.findById(id);
		return RetornoEncontrado.get();
	}
	
	public Optional<Retorno> buscarRetornoServico(Long idServico) {
		return this.retornoRepository.buscarRetornoServico(idServico);
	}

	@Override
	public List<Retorno> pesquisar(RetornoFilter filter) {
		return null;
	}
	
	public List<RetornoProjection> pesquisarNativo(RetornoFilter filter) {
		return this.retornoRepository.pesquisar(filter.getDataRef(), filter.getIdRegional(), filter.getIdTipoServico(), filter.getCodigoServico());
	}
}
