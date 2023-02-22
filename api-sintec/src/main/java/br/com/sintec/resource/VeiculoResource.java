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

import br.com.sintec.model.Veiculo;
import br.com.sintec.repository.filter.VeiculoFilter;
import br.com.sintec.service.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoResource implements IGenericResource<Veiculo, VeiculoFilter, Serializable> {

	@Autowired
	private VeiculoService veiculoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Veiculo entity) {
		Veiculo VeiculoSalvo = this.veiculoService.incluir(entity);
		return new ResponseEntity<Veiculo>(VeiculoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Veiculo entity) {
		Veiculo VeiculoAlterado = this.veiculoService.alterar(entity);
		return new ResponseEntity<Veiculo>(VeiculoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Veiculo VeiculoEncontrado = this.veiculoService.buscarPorId(id);
		return new ResponseEntity<Veiculo>(VeiculoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(VeiculoFilter filter) {
		List<Veiculo> lista = this.veiculoService.pesquisar(filter);
		return new ResponseEntity<List<Veiculo>>(lista,HttpStatus.OK);
	}
}
