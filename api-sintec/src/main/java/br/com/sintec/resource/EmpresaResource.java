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

import br.com.sintec.model.Empresa;
import br.com.sintec.repository.filter.EmpresaFilter;
import br.com.sintec.service.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaResource implements IGenericResource<Empresa, EmpresaFilter, Serializable> {

	
	@Autowired
	private EmpresaService empresaService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Empresa entity) {
		Empresa EmpresaSalvo = this.empresaService.incluir(entity);
		return new ResponseEntity<Empresa>(EmpresaSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Empresa entity) {
		Empresa EmpresaAlterado = this.empresaService.alterar(entity);
		return new ResponseEntity<Empresa>(EmpresaAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Empresa EmpresaEncontrado = this.empresaService.buscarPorId(id);
		return new ResponseEntity<Empresa>(EmpresaEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(EmpresaFilter filter) {
		List<Empresa> lista = this.empresaService.pesquisar(filter);
		return new ResponseEntity<List<Empresa>>(lista,HttpStatus.OK);
	}
}
