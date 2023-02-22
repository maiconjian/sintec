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

import br.com.sintec.model.Alternativa;
import br.com.sintec.repository.filter.AlternativaFilter;
import br.com.sintec.repository.projection.AlternativaMobileProjection;
import br.com.sintec.service.AlternativaService;

@RestController
@RequestMapping("/alternativa")
public class AlternativaResource implements IGenericResource<Alternativa, AlternativaFilter, Serializable> {

	@Autowired
	private AlternativaService alternativaService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Alternativa entity) {
		Alternativa AlternativaSalvo = this.alternativaService.incluir(entity);
		return new ResponseEntity<Alternativa>(AlternativaSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Alternativa entity) {
		Alternativa AlternativaAlterado = this.alternativaService.alterar(entity);
		return new ResponseEntity<Alternativa>(AlternativaAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Alternativa AlternativaEncontrado = this.alternativaService.buscarPorId(id);
		return new ResponseEntity<Alternativa>(AlternativaEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(AlternativaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GetMapping("/lista-alternativa-pergunta/{idPergunta}")
	public ResponseEntity<?> listaAlternativaPergunta(@PathVariable("idPergunta") Long idPergunta){
		List<Alternativa> lista = this.alternativaService.listaAlternativaPergunta(idPergunta);
		return new ResponseEntity<List<Alternativa>>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/get-alternativa-mobile/{idUsuario}")
	public ResponseEntity<?> getAlternativaMobile(@PathVariable("idUsuario") Long idUsuario){
		List<AlternativaMobileProjection> lista = this.alternativaService.getAlternativaMobile(idUsuario);
		return new ResponseEntity<List<AlternativaMobileProjection>>(lista, HttpStatus.OK);
	}
}
