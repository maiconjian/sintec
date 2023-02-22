package br.com.sintec.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.exception.CustomRuntimeException;
import br.com.sintec.model.Distribuicao;
import br.com.sintec.model.Servico;
import br.com.sintec.model.Usuario;
import br.com.sintec.model.dto.DistribuicaoDto;
import br.com.sintec.model.helpers.DistribuicaoEnum;
import br.com.sintec.repository.DistribuicaoRepository;
import br.com.sintec.repository.filter.DistribuicaoFilter;
import br.com.sintec.repository.projection.DistribuidoProjection;
import br.com.sintec.repository.projection.ServicoDistribuidoProjection;
import br.com.sintec.util.Apoio;
@Service
public class DistribuicaoService implements IGenericService<Distribuicao, DistribuicaoFilter, Serializable> {

	@Autowired
	private DistribuicaoRepository distribuicaoRepository;
	
	
	@Override
	public Distribuicao incluir(Distribuicao entity) {
		Distribuicao DistribuicaoSalvo = this.distribuicaoRepository.save(entity);
		return DistribuicaoSalvo;
	}
	
	public boolean associarServico(DistribuicaoDto dto) {
		List<Distribuicao> lista = new ArrayList<>();
		
		for (Long idServico : dto.getListaIdServico()) {
			Distribuicao distribuicao = new Distribuicao();
			distribuicao.setUsuario(new Usuario(dto.getIdUsuario()));
			distribuicao.setServico(new Servico(idServico));
			distribuicao.setFlagAcossiado(DistribuicaoEnum.DISTRIBUIDO.getValue());
			distribuicao.setDataAtualizacao(Apoio.getDateTimeFuso());
			lista.add(distribuicao);
		}
		
		try {
			this.distribuicaoRepository.saveAll(lista);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	
	@Override
	public Distribuicao alterar(Distribuicao entity) {
		Distribuicao DistribuicaoAlterado = this.distribuicaoRepository.save(entity);
		return DistribuicaoAlterado;
	}

	@Override
	public Distribuicao buscarPorId(long id) {
		Optional<Distribuicao> DistribuicaoEncontrado = this.distribuicaoRepository.findById(id);
		return DistribuicaoEncontrado.get();
	}

	@Override
	public List<Distribuicao> pesquisar(DistribuicaoFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<DistribuidoProjection> listaDistribuidoUsuario(DistribuicaoFilter filter){
		return  this.distribuicaoRepository.listaDistribuidoUsuario(filter.getIdRegional(), filter.getIdTipoServico(), filter.getDataRef(), filter.getIdUsuario());
	}
	
	public boolean desassociar(Long idServico,Long idUsuario) {
		try {
			this.distribuicaoRepository.incluirDesassociado(idServico, idUsuario);
			this.distribuicaoRepository.deletarDistribuicao(idServico, idUsuario);
			return true;
		} catch (Exception e) {
			throw new CustomRuntimeException("Erro");
		}
	
//		Integer qtd = this.distribuicaoRepository.deletarDistribuicao(idServico, idUsuario);
//		if(qtd > 0) {
//			this.distribuicaoRepository.incluirDesassociado(idServico, idUsuario);
//			return true;
//		}else {
//			throw new CustomRuntimeException("Serviço já executado");
//		}
	}
	
	public List<Long> getDesassociadoMobile(Long idUsuario){
		return this.distribuicaoRepository.getDesassociadoMobile(idUsuario);
	}
	
	public void deletarDesassociado(List<Long> listaIdServico) {
		this.distribuicaoRepository.deletarDesassociados(listaIdServico);
	}
	
	public List<ServicoDistribuidoProjection> listaDistribuido(DistribuicaoFilter filter){
		 return this.distribuicaoRepository.listaDistribuido(filter.getIdRegional(),filter.getIdTipoServico(), filter.getDataRef(),filter.getIdUsuario());
	 }
	
	public boolean liberarServico(List<Long> listaIdDistribuicao) {
		try {
			Integer qtdExec =  this.distribuicaoRepository.liberarServico(listaIdDistribuicao, Apoio.getDateTimeFuso());
			if(qtdExec > 0) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			throw new CustomRuntimeException("Erro ao executar!");
		}
	}
	
	public Integer cancelarLiberacao(Long idDistribuicao) {
		return this.distribuicaoRepository.cancelarLiberacao(idDistribuicao);
	}

	
  public Integer confirmarDoanloadServico(List<Long> idServico,Long idUsuario) {
	  return this.distribuicaoRepository.confirmarDownloadServico(idServico, idUsuario);
  }
}
