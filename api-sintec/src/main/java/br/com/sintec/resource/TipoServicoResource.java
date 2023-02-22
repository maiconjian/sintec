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

import br.com.sintec.model.TipoServico;
import br.com.sintec.repository.filter.TipoServicoFilter;
import br.com.sintec.service.TipoServicoService;

@RestController
@RequestMapping("/tipo-servico")
public class TipoServicoResource implements IGenericResource<TipoServico, TipoServicoFilter, Serializable> {

	@Autowired
	private TipoServicoService tipoServicoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody TipoServico entity) {
		TipoServico TipoServicoSalvo = this.tipoServicoService.incluir(entity);
		return new ResponseEntity<TipoServico>(TipoServicoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody TipoServico entity) {
		TipoServico TipoServicoAlterado = this.tipoServicoService.alterar(entity);
		return new ResponseEntity<TipoServico>(TipoServicoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		TipoServico TipoServicoEncontrado = this.tipoServicoService.buscarPorId(id);
		return new ResponseEntity<TipoServico>(TipoServicoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(TipoServicoFilter filter) {
		List<TipoServico> lista = this.tipoServicoService.pesquisar(filter);
		return new ResponseEntity<List<TipoServico>>(lista,HttpStatus.OK);
	}
}
