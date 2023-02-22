package br.com.sintec.resource;

import java.io.Serializable;

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

import br.com.sintec.model.RacpNConf;
import br.com.sintec.repository.filter.RacpNConfFilter;
import br.com.sintec.service.RacpNConfService;
@RestController
@RequestMapping("/racp-nconf")
public class RacpNConfResource implements IGenericResource<RacpNConf, RacpNConfFilter, Serializable> {

	@Autowired
	private RacpNConfService racpNConfService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody RacpNConf entity) {
		RacpNConf RacpNConfSalvo = this.racpNConfService.incluir(entity);
		return new ResponseEntity<RacpNConf>(RacpNConfSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody RacpNConf entity) {
		RacpNConf RacpNConfAlterado = this.racpNConfService.incluir(entity);
		return new ResponseEntity<RacpNConf>(RacpNConfAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		RacpNConf RacpNConfEncontrado = this.racpNConfService.buscarPorId(id);
		return new ResponseEntity<RacpNConf>(RacpNConfEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(RacpNConfFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
}
