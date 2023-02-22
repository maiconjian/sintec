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

import br.com.sintec.model.Imovel;
import br.com.sintec.repository.filter.ImovelFilter;
import br.com.sintec.service.ImovelService;

@RestController
@RequestMapping("/imovel")
public class ImovelResource implements IGenericResource<Imovel, ImovelFilter, Serializable> {

	@Autowired
	private ImovelService localidadeService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Imovel entity) {
		Imovel LocalidadeSalvo = this.localidadeService.incluir(entity);
		return new ResponseEntity<Imovel>(LocalidadeSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Imovel entity) {
		Imovel LocalidadeAlterado = this.localidadeService.alterar(entity);
		return new ResponseEntity<Imovel>(LocalidadeAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Imovel LocalidadeEncontrado = this.localidadeService.buscarPorId(id);
		return new ResponseEntity<Imovel>(LocalidadeEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(ImovelFilter filter) {
		List<Imovel> lista = this.localidadeService.pesquisar(filter);
		return new ResponseEntity<List<Imovel>>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/lista-localidade-regional/{idRegional}")
	public ResponseEntity<?> listaLocalidadePorRegional(@PathVariable("idRegional") Long idRegional){
		List<Imovel> lista =  this.localidadeService.listaLocalidadePorRegional(idRegional);
		return new ResponseEntity<List<Imovel>>(lista,HttpStatus.OK);
	}
	
	
	

	

}
