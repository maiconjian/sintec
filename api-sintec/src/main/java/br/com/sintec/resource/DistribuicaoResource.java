package br.com.sintec.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sintec.model.Distribuicao;
import br.com.sintec.model.dto.ConfirmacaoDownloadServicoDto;
import br.com.sintec.model.dto.DistribuicaoDto;
import br.com.sintec.repository.filter.DistribuicaoFilter;
import br.com.sintec.repository.projection.DistribuidoProjection;
import br.com.sintec.repository.projection.ServicoDistribuidoProjection;
import br.com.sintec.service.DistribuicaoService;

@RestController
@RequestMapping("/distribuicao")
public class DistribuicaoResource implements IGenericResource<Distribuicao, DistribuicaoFilter, Serializable> {


	@Autowired
	private DistribuicaoService distribuicaoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Distribuicao entity) {
		Distribuicao DistribuicaoSalvo = this.distribuicaoService.incluir(entity);
		return new ResponseEntity<Distribuicao>(DistribuicaoSalvo,HttpStatus.OK);
	}
	
	@PostMapping("/associar-servico")
	public boolean associarServico(@RequestBody DistribuicaoDto dto) {
		return this.distribuicaoService.associarServico(dto);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Distribuicao entity) {
		Distribuicao DistribuicaoAlterado = this.distribuicaoService.incluir(entity);
		return new ResponseEntity<Distribuicao>(DistribuicaoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Distribuicao DistribuicaoEncontrado = this.distribuicaoService.buscarPorId(id);
		return new ResponseEntity<Distribuicao>(DistribuicaoEncontrado,HttpStatus.OK);
	}

	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(DistribuicaoFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GetMapping("/lista-distribuido-usuario")
	public ResponseEntity<?> listaDistribuidoUsuario(DistribuicaoFilter filter){
		List<DistribuidoProjection> lista = this.distribuicaoService.listaDistribuidoUsuario(filter);
		return new ResponseEntity<List<DistribuidoProjection>>(lista,HttpStatus.OK);
	}
	
	@DeleteMapping("/desassociar/{idServico}/{idUsuario}")
	public boolean desassociar(@PathVariable("idServico")Long idServico,@PathVariable("idUsuario")Long idUsuario) {
		return this.distribuicaoService.desassociar(idServico, idUsuario);
	}
	
	@GetMapping("/get-desassociado-mobile/{idUsuario}")
	public ResponseEntity<?> getDesassociadoMobile(@PathVariable("idUsuario") Long idUsuario){
		 List<Long> lista = this.distribuicaoService.getDesassociadoMobile(idUsuario);
		 return new ResponseEntity<List<Long>>(lista,HttpStatus.OK);
	}
	
	@DeleteMapping("/deletar-desassociado")
	public void deletarDesassociados(@RequestBody List<Long> listaIdServico) {
		this.distribuicaoService.deletarDesassociado(listaIdServico);
	}
	
	
	@GetMapping("/lista-distribuido")
	public ResponseEntity<?> listaDistribuido(DistribuicaoFilter filter){
		List<ServicoDistribuidoProjection> lista =this.distribuicaoService.listaDistribuido(filter);
		return new ResponseEntity<List<ServicoDistribuidoProjection>>(lista,HttpStatus.OK);
	}
	
	@PostMapping("/liberar-servico")
	public boolean liberarServico(@RequestBody List<Long> listaIdDistribuicao) {
		return this.distribuicaoService.liberarServico(listaIdDistribuicao);
	}
	
	@DeleteMapping("/cancelar-liberacao/{idDistribuicao}")
	public Integer cancelarLiberacao(@PathVariable("idDistribuicao") Long idDistribuicao) {
		return this.distribuicaoService.cancelarLiberacao(idDistribuicao);
	}
	
	@PostMapping("/confirmar-download-servico")
	public Integer confirmarDownloadServico(@RequestBody ConfirmacaoDownloadServicoDto dto) {
		return this.distribuicaoService.confirmarDoanloadServico(dto.getListaIdServico(), dto.getIdUsuario());
	}
	
}
