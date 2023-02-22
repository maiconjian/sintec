package br.com.sintec.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sintec.model.CategoriaTipoServico;
import br.com.sintec.repository.filter.CategoriaTipoServicoFilter;
import br.com.sintec.service.CategoriaTipoServicoService;

@RestController
@RequestMapping("/categoria-tipo-servico")
public class CategoriaTipoServicoResource implements IGenericResource<CategoriaTipoServico, CategoriaTipoServicoFilter, Serializable> {

	@Autowired
	private CategoriaTipoServicoService service;
	
	@Override
	public ResponseEntity<?> incluir(CategoriaTipoServico entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> alterar(CategoriaTipoServico entity) throws Exception {
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
	public ResponseEntity<?> pesquisar(CategoriaTipoServicoFilter filter) {
		List<CategoriaTipoServico> lista = this.service.pesquisar(filter);
		return new ResponseEntity<List<CategoriaTipoServico>>(lista,HttpStatus.OK);
	}

}
