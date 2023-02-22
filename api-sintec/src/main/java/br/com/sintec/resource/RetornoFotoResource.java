package br.com.sintec.resource;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import br.com.sintec.config.ApiProperty;
import br.com.sintec.model.RetornoFoto;
import br.com.sintec.model.dto.RetornoFotoDto;
import br.com.sintec.repository.filter.RetornoFotoFilter;
import br.com.sintec.repository.projection.RetornoFotoProjection;
import br.com.sintec.service.RetornoFotoService;
@RestController
@RequestMapping("/retorno-foto")
public class RetornoFotoResource implements IGenericResource<RetornoFoto, RetornoFotoFilter, Serializable>{
	
	@Autowired
	private RetornoFotoService retornoFotoService;
	@Autowired
	private ApiProperty property;
	
	@Override
	public ResponseEntity<?> incluir(RetornoFoto entity) {
		RetornoFoto RetornoFotoSalvo = this.retornoFotoService.incluir(entity);
		return new ResponseEntity<RetornoFoto>(RetornoFotoSalvo,HttpStatus.OK);
	}

	@Override
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody RetornoFoto entity) {
		RetornoFoto RetornoFotoAlterado = this.retornoFotoService.incluir(entity);
		return new ResponseEntity<RetornoFoto>(RetornoFotoAlterado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") long id) {
		RetornoFoto RetornoFotoEncontrado = this.retornoFotoService.buscarPorId(id);
		return new ResponseEntity<RetornoFoto>(RetornoFotoEncontrado,HttpStatus.OK);
	}

	@Override
	@GetMapping("/pesquisar")
	public ResponseEntity<?> pesquisar(RetornoFotoFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PostMapping("/incluir")
	public ResponseEntity<?> incluir(@RequestBody List<RetornoFotoDto> listaDto) throws Exception{
		List<Long> listaIdServico = this.retornoFotoService.incluir(listaDto);
		return new ResponseEntity<List<Long>>(listaIdServico,HttpStatus.OK);
	}
	
	@GetMapping("/getfoto/{caminho}")
	public void getFoto(@PathVariable("caminho") String caminho,HttpServletResponse response) throws Exception {
////		caminho = caminho.replace("-", "/");
//		File imagem = new File(this.property.getPathRaiz()+caminho);
//		System.out.println(this.property.getPathRaiz()+caminho);
//		if (imagem.length() >= 0) {
//			Files.copy(imagem.toPath(), response.getOutputStream());
//		} else {
//			imagem = new File(this.property.getPathRaiz()+"/sem_imagem.png");
//			Files.copy(imagem.toPath(), response.getOutputStream());
//		}
		try {
			String caminhoFoto = this.property.getPathRaiz();
			String pathFoto = caminho.replaceAll("-", "/");
			caminhoFoto += pathFoto;
			File imagem = new File(caminhoFoto);
			Files.copy(imagem.toPath(), response.getOutputStream());	
		} catch (Exception e) {
			System.out.println("erro ao buscar foto: " + e.getMessage());
		}
	}
	
	@GetMapping("/get-retorno-foto/{idServico}")
	public ResponseEntity<?> getRetornoFoto(@PathVariable("idServico") Long idServico){
	  List<RetornoFotoProjection> lista = this.retornoFotoService.getRetornoProjection(idServico);
	  return new ResponseEntity<List<RetornoFotoProjection>>(lista,HttpStatus.OK);
	}
}
