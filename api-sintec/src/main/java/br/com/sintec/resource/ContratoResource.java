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

import br.com.sintec.model.Contrato;
import br.com.sintec.repository.filter.ContratoFilter;
import br.com.sintec.service.ContratoService;

@RestController
@RequestMapping("/contrato")
public class ContratoResource implements IGenericResource<Contrato, ContratoFilter, Serializable> {

	@Autowired
	private ContratoService contratoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Contrato entity) {
		Contrato ContratoSalvo = this.contratoService.incluir(entity);
		return new ResponseEntity<Contrato>(ContratoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Contrato entity) {
		Contrato ContratoAlterado = this.contratoService.alterar(entity);
		return new ResponseEntity<Contrato>(ContratoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Contrato ContratoEncontrado = this.contratoService.buscarPorId(id);
		return new ResponseEntity<Contrato>(ContratoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(ContratoFilter filter) {
		List<Contrato> lista = this.contratoService.pesquisar(filter);
		return new ResponseEntity<List<Contrato>>(lista,HttpStatus.OK);
	}
}
