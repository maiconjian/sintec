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

import br.com.sintec.model.PerfilAcesso;
import br.com.sintec.repository.filter.PerfilAcessoFilter;
import br.com.sintec.service.PerfilAcessoService;

@RestController
@RequestMapping("/perfil")
public class PerfilAcessoResource implements IGenericResource<PerfilAcesso, PerfilAcessoFilter, Serializable> {

	@Autowired
	private PerfilAcessoService perfilAcessoService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody PerfilAcesso entity) {
		PerfilAcesso PerfilAcessoSalvo = this.perfilAcessoService.incluir(entity);
		return new ResponseEntity<PerfilAcesso>(PerfilAcessoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody PerfilAcesso entity) {
		PerfilAcesso PerfilAcessoAlterado = this.perfilAcessoService.incluir(entity);
		return new ResponseEntity<PerfilAcesso>(PerfilAcessoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		PerfilAcesso PerfilAcessoEncontrado = this.perfilAcessoService.buscarPorId(id);
		return new ResponseEntity<PerfilAcesso>(PerfilAcessoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(PerfilAcessoFilter filter) {
		List<PerfilAcesso> lista = this.perfilAcessoService.pesquisar(filter);
		return new ResponseEntity<List<PerfilAcesso>>(lista,HttpStatus.OK);
	}
	
}
