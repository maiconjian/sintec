package br.com.sintec.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sintec.model.CategoriaVeiculo;
import br.com.sintec.repository.filter.CategoriaVeiculoFilter;
import br.com.sintec.service.CategoriaVeiculoService;

@RestController
@RequestMapping("/categoria-veiculo")
public class CategoriaVeiculoResource implements IGenericResource<CategoriaVeiculo, CategoriaVeiculoFilter, Serializable> {
	
	@Autowired
	private CategoriaVeiculoService service;
	
	@Override
	public ResponseEntity<?> incluir(CategoriaVeiculo entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> alterar(CategoriaVeiculo entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> buscarPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(CategoriaVeiculoFilter filter) {
		List<CategoriaVeiculo> lista = this.service.pesquisar(filter);
		return new ResponseEntity<List<CategoriaVeiculo>>(lista,HttpStatus.OK);
	}

}
