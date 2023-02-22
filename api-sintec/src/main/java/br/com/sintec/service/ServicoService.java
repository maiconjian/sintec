package br.com.sintec.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Colaborador;
import br.com.sintec.model.Imovel;
import br.com.sintec.model.Regional;
import br.com.sintec.model.Servico;
import br.com.sintec.model.TipoServico;
import br.com.sintec.model.Veiculo;
import br.com.sintec.model.dto.AgendarServicoDto;
import br.com.sintec.model.helpers.CategoriaTipoServicoEnum;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.ServicoRepository;
import br.com.sintec.repository.filter.ServicoFilter;
import br.com.sintec.repository.projection.ServicoLiberadoMobileProjection;
import br.com.sintec.repository.projection.ServicoPendenteProjection;
import br.com.sintec.util.Apoio;
@Service
public class ServicoService implements IGenericService<Servico, ServicoFilter, Serializable> {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private TipoServicoService tipoServicoService;
	
//	@Autowired
//	private LocalidadeService localidadeService;
	
	
	@Override
	public Servico incluir(Servico entity) {
		Servico ServicoSalvo = this.servicoRepository.save(entity);
		return ServicoSalvo;
	}

	@Override
	public Servico alterar(Servico entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Servico ServicoAlterado = this.servicoRepository.save(entity);
		return ServicoAlterado;
	}

	@Override
	public Servico buscarPorId(long id) {
		Optional<Servico> ServicoEncontrado = this.servicoRepository.findById(id);
		return ServicoEncontrado.get();
	}

	@Override
	public List<Servico> pesquisar(ServicoFilter filter) {
//		return this.servicoRepository.pesquisar(filter);
		return null;
	}
	
	public List<ServicoPendenteProjection> listaServicoPendente(ServicoFilter filter){
		return this.servicoRepository.listaServicoPendente(filter.getIdRegional(), filter.getIdTipoServico(), filter.getDataRef(),filter.getAtivo());
	}
	
	public Iterable<Servico> agendarServico(AgendarServicoDto dto) {
		TipoServico tipoServico = this.tipoServicoService.buscarPorId(dto.getIdTipoServico());
		LocalDate dataProgramada = dto.getNumDiasAgendamento() == 0 ? dto.getDataSolicitacao().plusDays(tipoServico.getNumDiasAgendamento()):
			dto.getDataSolicitacao().plusDays(dto.getNumDiasAgendamento());
		
		List<Servico> listaParaAgendar = new ArrayList<>();
		Iterable<Servico> listaServicoAgendado = new ArrayList<>();
		
		if(dto.getIdCategoriaTipoServico() == CategoriaTipoServicoEnum.VEICULO.getValue()){
			listaParaAgendar = montarListaServicoVeiculo(dto, tipoServico, dataProgramada);
		}else if(dto.getIdCategoriaTipoServico() == CategoriaTipoServicoEnum.LOCAL.getValue()) {
			listaParaAgendar = montarListaServicoImovel(dto, tipoServico, dataProgramada);
		}else if(dto.getIdCategoriaTipoServico() == CategoriaTipoServicoEnum.FUNCIONARIO.getValue()) {
			listaParaAgendar = montarListaServicoColaborador(dto, tipoServico, dataProgramada);
		}
		
		listaServicoAgendado = this.servicoRepository.saveAll(listaParaAgendar);
		return insertCodigoServico(listaServicoAgendado,dto.getDataSolicitacao());
	}
	
	private List<Servico> montarListaServicoColaborador(AgendarServicoDto dto,TipoServico tipoServico,LocalDate dataProgramada){	
		List<Servico> lista = new ArrayList<>();		
		for (Colaborador colaborador : dto.getListaColaborador()) {
			Servico servico = new Servico();
			servico.setDataRef(dto.getDataSolicitacao().withDayOfMonth(1));
			servico.setTipoServico(tipoServico);
			servico.setRegional(new Regional(dto.getIdRegional()));
			servico.setColaborador(colaborador);
			servico.setNumDiasAgendamento(dto.getNumDiasAgendamento() == 0 ? tipoServico.getNumDiasAgendamento() : dto.getNumDiasAgendamento());
			servico.setDataSolicitacao(dto.getDataSolicitacao());
			servico.setDataProgramada(dataProgramada);
			servico.setDataAtualizacao(Apoio.getDateTimeFuso());
			servico.setFlagAvulsa(0);
			servico.setAtivo(SituacaoEnum.ATIVO.getValue());
			lista.add(servico);
		}
		return lista;
	}
	
	private List<Servico> montarListaServicoImovel(AgendarServicoDto dto,TipoServico tipoServico,LocalDate dataProgramada){	
		List<Servico> lista = new ArrayList<>();		
		for (Imovel imovel : dto.getListaImovel()) {
			Servico servico = new Servico();
			servico.setDataRef(dto.getDataSolicitacao().withDayOfMonth(1));
			servico.setTipoServico(tipoServico);
			servico.setRegional(new Regional(dto.getIdRegional()));
			servico.setImovel(imovel);
			servico.setNumDiasAgendamento(dto.getNumDiasAgendamento() == 0 ? tipoServico.getNumDiasAgendamento() : dto.getNumDiasAgendamento());
			servico.setDataSolicitacao(dto.getDataSolicitacao());
			servico.setDataProgramada(dataProgramada);
			servico.setDataAtualizacao(Apoio.getDateTimeFuso());
			servico.setFlagAvulsa(0);
			servico.setAtivo(SituacaoEnum.ATIVO.getValue());
			lista.add(servico);
		}
		return lista;
		
	}
	
	private List<Servico> montarListaServicoVeiculo(AgendarServicoDto dto,TipoServico tipoServico,LocalDate dataProgramada){	
		List<Servico> lista = new ArrayList<>();		
		for (Veiculo veiculo : dto.getListaVeiculo()) {
			Servico servico = new Servico();
			servico.setDataRef(dto.getDataSolicitacao().withDayOfMonth(1));
			servico.setTipoServico(tipoServico);
			servico.setRegional(new Regional(dto.getIdRegional()));
			servico.setVeiculo(veiculo);
			servico.setColaborador(veiculo.getColaborador());
			servico.setNumDiasAgendamento(dto.getNumDiasAgendamento() == 0 ? tipoServico.getNumDiasAgendamento() : dto.getNumDiasAgendamento());
			servico.setDataSolicitacao(dto.getDataSolicitacao());
			servico.setDataProgramada(dataProgramada);
			servico.setDataAtualizacao(Apoio.getDateTimeFuso());
			servico.setFlagAvulsa(0);
			servico.setAtivo(SituacaoEnum.ATIVO.getValue());
			lista.add(servico);
		}
		return lista;
		
	}
	
	
	private Iterable<Servico> insertCodigoServico(Iterable<Servico> listaServicoSalvo,LocalDate dataSolicitacao){
		for (Servico servico : listaServicoSalvo) {
			String mes = dataSolicitacao.getMonthValue()+"";
			String ano = dataSolicitacao.getYear()+"";
			String codigo = ano.substring(2,4)+mes+servico.getId().toString();
			servico.setCodigo(codigo);
		}
		return this.servicoRepository.saveAll(listaServicoSalvo);
	}
	
	
//	Mobile
	public List<ServicoLiberadoMobileProjection> getServicoLiberadoMobile(Long idUsuario){
		return this.servicoRepository.getServicoLiberadoMobile(idUsuario);
	}
	
	
	
	
	
}
