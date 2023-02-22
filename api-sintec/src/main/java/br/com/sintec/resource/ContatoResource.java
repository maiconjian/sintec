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

import br.com.sintec.model.Contato;
import br.com.sintec.repository.filter.ContatoFilter;
import br.com.sintec.service.ContatoService;

@RestController
@RequestMapping("/contato")
public class ContatoResource implements IGenericResource<Contato, ContatoFilter, Serializable> {

	
	@Autowired
	private ContatoService contatoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Contato entity) {
		Contato ContatoSalvo = this.contatoService.incluir(entity);
		return new ResponseEntity<Contato>(ContatoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Contato entity) {
		Contato ContatoAlterado = this.contatoService.alterar(entity);
		return new ResponseEntity<Contato>(ContatoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Contato ContatoEncontrado = this.contatoService.buscarPorId(id);
		return new ResponseEntity<Contato>(ContatoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(ContatoFilter filter) {
		List<Contato> lista = this.contatoService.pesquisar(filter);
		return new ResponseEntity<List<Contato>>(lista,HttpStatus.OK);
	}
	
	@DeleteMapping("/deletar/{id}")
	public void deletar(@PathVariable("id") Long id) {
		this.contatoService.deletar(id);
	}
}
