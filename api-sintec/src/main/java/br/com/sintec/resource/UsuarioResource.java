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

import br.com.sintec.model.Usuario;
import br.com.sintec.repository.filter.UsuarioFilter;
import br.com.sintec.repository.projection.UsuarioMobileProjection;
import br.com.sintec.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource implements IGenericResource<Usuario, UsuarioFilter, Serializable>{

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Usuario entity) throws Exception {
		Usuario UsuarioSalvo = this.usuarioService.incluir(entity);
		return new ResponseEntity<Usuario>(UsuarioSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Usuario entity) throws Exception {
		Usuario UsuarioAlterado = this.usuarioService.alterar(entity);
		return new ResponseEntity<Usuario>(UsuarioAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Usuario UsuarioEncontrado = this.usuarioService.buscarPorId(id);
		return new ResponseEntity<Usuario>(UsuarioEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(UsuarioFilter filter) {
		List<Usuario> lista = this.usuarioService.pesquisar(filter);
		return new ResponseEntity<List<Usuario>>(lista,HttpStatus.OK);
	}
	
	@GetMapping("/reset-password/{id}")
	public ResponseEntity<?> resetPassword(@PathVariable("id") long id) throws Exception {
		Usuario usuario = this.usuarioService.resetPassword(id);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@GetMapping("/get-usuario-mobile")
	public ResponseEntity<?> getUsuarioMobile(){
		List<UsuarioMobileProjection> lista = this.usuarioService.getUsuarioMobile();
		return new ResponseEntity<List<UsuarioMobileProjection>>(lista, HttpStatus.OK);
	}
}
