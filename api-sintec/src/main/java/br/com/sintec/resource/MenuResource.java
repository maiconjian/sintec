package br.com.sintec.resource;

import java.io.Serializable;

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

import br.com.sintec.model.Menu;
import br.com.sintec.repository.filter.MenuFilter;
import br.com.sintec.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuResource implements IGenericResource<Menu, MenuFilter, Serializable> {
	
	@Autowired
	private MenuService menuService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Menu entity) {
		Menu menuSalvo = this.menuService.incluir(entity);
		return new ResponseEntity<Menu>(menuSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Menu entity) {
		Menu menuAlterado = this.menuService.incluir(entity);
		return new ResponseEntity<Menu>(menuAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Menu menuEncontrado = this.menuService.buscarPorId(id);
		return new ResponseEntity<Menu>(menuEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(MenuFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
