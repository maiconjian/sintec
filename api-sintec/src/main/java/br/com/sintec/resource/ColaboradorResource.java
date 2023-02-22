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

import br.com.sintec.model.Colaborador;
import br.com.sintec.repository.filter.ColaboradorFilter;
import br.com.sintec.repository.projection.ColaboradorProjection;
import br.com.sintec.service.ColaboradorService;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorResource implements IGenericResource<Colaborador, ColaboradorFilter, Serializable>{

	
	@Autowired
	private ColaboradorService colaboradorService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Colaborador entity) {
		Colaborador ColaboradorSalvo = this.colaboradorService.incluir(entity);
		return new ResponseEntity<Colaborador>(ColaboradorSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Colaborador entity) {
		Colaborador ColaboradorAlterado = this.colaboradorService.alterar(entity);
		return new ResponseEntity<Colaborador>(ColaboradorAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Colaborador ColaboradorEncontrado = this.colaboradorService.buscarPorId(id);
		return new ResponseEntity<Colaborador>(ColaboradorEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(ColaboradorFilter filter) {
		List<Colaborador> lista = this.colaboradorService.pesquisar(filter);
		return new ResponseEntity<List<Colaborador>>(lista,HttpStatus.OK);
	}
	
	@GetMapping(value="/lista-colaborador-sem-veiculo/{idRegional}")
	public ResponseEntity<?> listaColaboradorSemVeiculo(Long idRegional){
		List<ColaboradorProjection> lista =  this.colaboradorService.listaColaboradorSemVeiculo(idRegional);
		return new ResponseEntity<List<ColaboradorProjection>>(lista,HttpStatus.OK);
	}
	
}


