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

import br.com.sintec.model.Regional;
import br.com.sintec.repository.filter.RegionalFilter;
import br.com.sintec.service.RegionalService;

@RestController
@RequestMapping("/regional")
public class RegionalResource implements IGenericResource<Regional, RegionalFilter, Serializable> {

	@Autowired
	private RegionalService regionalService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Regional entity) {
		Regional RegionalSalvo = this.regionalService.incluir(entity);
		return new ResponseEntity<Regional>(RegionalSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Regional entity) {
		Regional RegionalAlterado = this.regionalService.alterar(entity);
		return new ResponseEntity<Regional>(RegionalAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Regional RegionalEncontrado = this.regionalService.buscarPorId(id);
		return new ResponseEntity<Regional>(RegionalEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(RegionalFilter filter) {
		List<Regional> lista = this.regionalService.pesquisar(filter);
		return new ResponseEntity<List<Regional>>(lista,HttpStatus.OK);
	}
}
