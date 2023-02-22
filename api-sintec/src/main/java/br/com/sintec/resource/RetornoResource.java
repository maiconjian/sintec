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

import br.com.sintec.model.Retorno;
import br.com.sintec.model.dto.RetornoDto;
import br.com.sintec.repository.filter.RetornoFilter;
import br.com.sintec.repository.projection.RetornoProjection;
import br.com.sintec.service.RetornoService;
@RestController
@RequestMapping("/retorno")
public class RetornoResource implements IGenericResource<Retorno, RetornoFilter, Serializable> {

	@Autowired
	private RetornoService retornoService;
	
	@Override
	public ResponseEntity<?> incluir(@RequestBody Retorno entity) {
		Retorno RetornoSalvo = this.retornoService.incluir(entity);
		return new ResponseEntity<Retorno>(RetornoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Retorno entity) {
		Retorno RetornoAlterado = this.retornoService.incluir(entity);
		return new ResponseEntity<Retorno>(RetornoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Retorno RetornoEncontrado = this.retornoService.buscarPorId(id);
		return new ResponseEntity<Retorno>(RetornoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(RetornoFilter filter) {
		List<RetornoProjection> lista = this.retornoService.pesquisarNativo(filter);
		return new ResponseEntity<List<RetornoProjection>>(lista,HttpStatus.OK);
	}
	
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody List<RetornoDto> listaRetorno){
		List<Long> lista = this.retornoService.incluir(listaRetorno);
		return new ResponseEntity<List<Long>>(lista,HttpStatus.OK);
	}

}
