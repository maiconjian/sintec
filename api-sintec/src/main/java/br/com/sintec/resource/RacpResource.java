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

import br.com.sintec.model.Racp;
import br.com.sintec.repository.filter.RacpFilter;
import br.com.sintec.service.RacpService;

@RestController
@RequestMapping("/racp")
public class RacpResource implements IGenericResource<Racp, RacpFilter, Serializable> {

	@Autowired
	private RacpService racpService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Racp entity) {
		Racp RacpSalvo = this.racpService.incluir(entity);
		return new ResponseEntity<Racp>(RacpSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Racp entity) {
		Racp RacpAlterado = this.racpService.incluir(entity);
		return new ResponseEntity<Racp>(RacpAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Racp RacpEncontrado = this.racpService.buscarPorId(id);
		return new ResponseEntity<Racp>(RacpEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(RacpFilter filter) {
		List<Racp> lista = this.racpService.pesquisar(filter);
		return new ResponseEntity<List<Racp>>(lista,HttpStatus.OK);
	}
}
