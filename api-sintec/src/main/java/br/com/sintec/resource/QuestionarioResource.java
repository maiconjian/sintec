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

import br.com.sintec.model.Questionario;
import br.com.sintec.repository.filter.QuestionarioFilter;
import br.com.sintec.repository.projection.QuestionarioMobileProjection;
import br.com.sintec.service.QuestionarioService;

@RestController
@RequestMapping("/questionario")
public class QuestionarioResource implements IGenericResource<Questionario, QuestionarioFilter, Serializable>{

	@Autowired
	private QuestionarioService questionarioService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Questionario entity) {
		Questionario questionarioSalvo = this.questionarioService.incluir(entity);
		return new ResponseEntity<Questionario>(questionarioSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Questionario entity) {
		Questionario questionarioAlterado = this.questionarioService.incluir(entity);
		return new ResponseEntity<Questionario>(questionarioAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Questionario questionarioEncontrado = this.questionarioService.buscarPorId(id);
		return new ResponseEntity<Questionario>(questionarioEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(QuestionarioFilter filter) {
		List<Questionario> lista = this.questionarioService.pesquisar(filter);
		return new ResponseEntity<List<Questionario>>(lista,HttpStatus.OK);
	}
	
	@PutMapping("/alterar-situacao-questionario")
	public ResponseEntity<?> alterarSituacaoQuestionario(@RequestBody Questionario entity) {
		 Questionario questionarioAlterado = this.questionarioService.alterarSituacaoQuestionario(entity);
		 return new ResponseEntity<Questionario>(questionarioAlterado,HttpStatus.OK);
	}
	
	@GetMapping("/get-questionario-mobile/{idUsuario}")
	public ResponseEntity<?> getQuestionarioMobile(@PathVariable("idUsuario")Long idUsuario){
		List<QuestionarioMobileProjection> lista = this.questionarioService.getQuestionarioMobile(idUsuario);
		return new ResponseEntity<List<QuestionarioMobileProjection>>(lista,HttpStatus.OK);
	}
}
