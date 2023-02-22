package br.com.sintec.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sintec.model.SituacaoRacp;
import br.com.sintec.repository.filter.SituacaoRacpFilter;
import br.com.sintec.service.SituacaoRacpService;

@RestController
@RequestMapping("/situacao-racp")
public class SituacaoRacpResource  implements IGenericResource<SituacaoRacp, SituacaoRacpFilter, Serializable> {
	
	@Autowired
	private SituacaoRacpService service;

	@Override
	public ResponseEntity<?> incluir(SituacaoRacp entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> alterar(SituacaoRacp entity) throws Exception {
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
	public ResponseEntity<?> pesquisar(SituacaoRacpFilter filter) {
		List<SituacaoRacp> lista = this.service.pesquisar(filter);
		return new ResponseEntity<List<SituacaoRacp>>(lista,HttpStatus.OK);
	}

}
