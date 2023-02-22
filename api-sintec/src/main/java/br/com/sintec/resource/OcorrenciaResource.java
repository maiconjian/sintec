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

import br.com.sintec.model.Ocorrencia;
import br.com.sintec.repository.filter.OcorrenciaFilter;
import br.com.sintec.repository.projection.OcorrenciaMobileProjection;
import br.com.sintec.service.OcorrenciaService;
@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaResource implements IGenericResource<Ocorrencia, OcorrenciaFilter, Serializable> {

	@Autowired
	private OcorrenciaService ocorrenciaService;
	
	@Override
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody Ocorrencia entity) {
		Ocorrencia OcorrenciaSalvo = this.ocorrenciaService.incluir(entity);
		return new ResponseEntity<Ocorrencia>(OcorrenciaSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody Ocorrencia entity) {
		Ocorrencia OcorrenciaAlterado = this.ocorrenciaService.incluir(entity);
		return new ResponseEntity<Ocorrencia>(OcorrenciaAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		Ocorrencia OcorrenciaEncontrado = this.ocorrenciaService.buscarPorId(id);
		return new ResponseEntity<Ocorrencia>(OcorrenciaEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(OcorrenciaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GetMapping("/get-ocorrencia-mobile/{idUsuario}")
	public ResponseEntity<?> getOcorrenciaMobile(@PathVariable("idUsuario") Long idUsuario){
		List<OcorrenciaMobileProjection> lista = this.ocorrenciaService.getOcorrenciaMovile(idUsuario);
		return new ResponseEntity<List<OcorrenciaMobileProjection>>(lista,HttpStatus.OK);
	}
}
