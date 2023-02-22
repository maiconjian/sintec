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

import br.com.sintec.model.Pergunta;
import br.com.sintec.repository.filter.PerguntaFilter;
import br.com.sintec.repository.projection.PerguntaMobileProjection;
import br.com.sintec.service.PerguntaService;

@RestController
@RequestMapping("/pergunta")
public class PerguntaResource implements IGenericResource<Pergunta, PerguntaFilter, Serializable> {

	@Autowired
	private PerguntaService perguntaService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Pergunta entity) {
		Pergunta PerguntaSalvo = this.perguntaService.incluir(entity);
		return new ResponseEntity<Pergunta>(PerguntaSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Pergunta entity) {
		Pergunta PerguntaAlterado = this.perguntaService.incluir(entity);
		return new ResponseEntity<Pergunta>(PerguntaAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Pergunta PerguntaEncontrado = this.perguntaService.buscarPorId(id);
		return new ResponseEntity<Pergunta>(PerguntaEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(PerguntaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GetMapping("/lista-pergunta-questionario/{idQuestionario}")
	public ResponseEntity<?> listaPerguntaQuestionario(@PathVariable("idQuestionario") Long idQuestionario){
		List<Pergunta> lista = this.perguntaService.listaPerguntaQuestionario(idQuestionario);
		return new ResponseEntity<List<Pergunta>>(lista,HttpStatus.OK);
	}
	@PutMapping("/alterar-situacao-pergunta")
	public ResponseEntity<?> alterarSituacaoPergunta(@RequestBody Pergunta entity) {
		Pergunta perguntaDesativada = this.perguntaService.alterarSituacaoPergunta(entity);
		return new ResponseEntity<Pergunta>(perguntaDesativada,HttpStatus.OK); 
	}
	
	@GetMapping("/get-pergunta-mobile/{idUsuario}")
	public ResponseEntity<?> getPerguntaMobile(@PathVariable("idUsuario") Long idUsuario){
		List<PerguntaMobileProjection> lista = this.perguntaService.getPerguntaMobile(idUsuario);
		return new ResponseEntity<List<PerguntaMobileProjection>>(lista,HttpStatus.OK);
	}
}
