package br.com.sintec.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sintec.model.Servico;
import br.com.sintec.model.dto.AgendarServicoDto;
import br.com.sintec.repository.filter.ServicoFilter;
import br.com.sintec.repository.projection.ServicoLiberadoMobileProjection;
import br.com.sintec.repository.projection.ServicoPendenteProjection;
import br.com.sintec.service.ServicoService;

@RestController
@RequestMapping("/servico")
public class ServicoResource implements IGenericResource<Servico, ServicoFilter, Serializable>{

	@Autowired
	private ServicoService servicoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Servico entity) {
		Servico ServicoSalvo = this.servicoService.incluir(entity);
		return new ResponseEntity<Servico>(ServicoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Servico entity) {
		Servico ServicoAlterado = this.servicoService.alterar(entity);
		return new ResponseEntity<Servico>(ServicoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Servico ServicoEncontrado = this.servicoService.buscarPorId(id);
		return new ResponseEntity<Servico>(ServicoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(ServicoFilter filter) {
//		List<Servico> lista = this.servicoService.pesquisar(filter);
//		return new ResponseEntity<List<Servico>>(lista,HttpStatus.OK);
		return null;
	}
	
	@GetMapping("/lista-servico-pendente")
	public ResponseEntity<?> listaServicoPendente(ServicoFilter filter){
		List<ServicoPendenteProjection> lista = this.servicoService.listaServicoPendente(filter);
		return new ResponseEntity<List<ServicoPendenteProjection>>(lista,HttpStatus.OK);
	}
	
	@PostMapping("/agendar-servico")
	public ResponseEntity<?> agendarServico(@RequestBody AgendarServicoDto dto){
		 Iterable<Servico> lista =this.servicoService.agendarServico(dto);
		 return new ResponseEntity<Iterable<Servico>>(lista,HttpStatus.OK);
	}
	
//	Mobile
	@GetMapping("/get-servico-liberado/{idUsuario}")
	public ResponseEntity<?> getServicoLiberadoMobile(@PathVariable("idUsuario") Long idUsuario){
		List<ServicoLiberadoMobileProjection> lista = this.servicoService.getServicoLiberadoMobile(idUsuario);
		return new ResponseEntity<List<ServicoLiberadoMobileProjection>>(lista,HttpStatus.OK);
	}
	
}
