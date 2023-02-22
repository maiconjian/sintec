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

import br.com.sintec.model.RetornoResposta;
import br.com.sintec.model.dto.RetornoRespostaDto;
import br.com.sintec.repository.filter.RetornoRespostaFilter;
import br.com.sintec.repository.projection.RetornoRespostaProjection;
import br.com.sintec.service.RetornoRespostaService;

@RestController
@RequestMapping("/retorno-resposta")
public class RetornoRespostaResource implements IGenericResource<RetornoResposta, RetornoRespostaFilter, Serializable> {


	@Autowired
	private RetornoRespostaService retornoRespostaService;
	
	@Override
	public ResponseEntity<?> incluir( RetornoResposta entity) {
		RetornoResposta RetornoRespostaSalvo = this.retornoRespostaService.incluir(entity);
		return new ResponseEntity<RetornoResposta>(RetornoRespostaSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody RetornoResposta entity) {
		RetornoResposta RetornoRespostaAlterado = this.retornoRespostaService.incluir(entity);
		return new ResponseEntity<RetornoResposta>(RetornoRespostaAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		RetornoResposta RetornoRespostaEncontrado = this.retornoRespostaService.buscarPorId(id);
		return new ResponseEntity<RetornoResposta>(RetornoRespostaEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(RetornoRespostaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody List<RetornoRespostaDto> listaDto){
		List<Long> lista = this.retornoRespostaService.inlcuir(listaDto);
		return new ResponseEntity<List<Long>>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/get-respostas/{idServico}")
	public ResponseEntity<?> getRespostas(@PathVariable("idServico") Long idServico){
		List<RetornoRespostaProjection> lista = this.retornoRespostaService.getRespostas(idServico);
		return new ResponseEntity<List<RetornoRespostaProjection>>(lista,HttpStatus.OK);
	}
}
