package br.com.sintec.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.config.ApiProperty;
import br.com.sintec.exception.CustomRuntimeException;
import br.com.sintec.model.Questionario;
import br.com.sintec.model.RetornoFoto;
import br.com.sintec.model.Servico;
import br.com.sintec.model.dto.RetornoFotoDto;
import br.com.sintec.repository.RetornoFotoRepository;
import br.com.sintec.repository.filter.RetornoFotoFilter;
import br.com.sintec.repository.projection.RetornoFotoProjection;

@Service
public class RetornoFotoService implements IGenericService<RetornoFoto, RetornoFotoFilter, Serializable>{
	
	@Autowired
	private RetornoFotoRepository retornoFotoRepository;
	
	@Autowired
	private ApiProperty apiProperty;
	
//	@Autowired
//	private RetornoService retornoService;
	
	
	@Override
	public RetornoFoto incluir(RetornoFoto entity) {
		RetornoFoto RetornoFotoSalvo = this.retornoFotoRepository.save(entity);
		return RetornoFotoSalvo;
	}

	@Override
	public RetornoFoto alterar(RetornoFoto entity) {
		RetornoFoto RetornoFotoAlterado = this.retornoFotoRepository.save(entity);
		return RetornoFotoAlterado;
	}

	@Override
	public RetornoFoto buscarPorId(long id) {
		Optional<RetornoFoto> RetornoFotoEncontrado = this.retornoFotoRepository
				.findById(id);
		return RetornoFotoEncontrado.get();
	}

	@Override
	public List<RetornoFoto> pesquisar(RetornoFotoFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Long> incluir(List<RetornoFotoDto> listaDto) throws Exception{
		List<Long> listaIdServico = new ArrayList<>(); 
		List<RetornoFoto> listaRetornoFoto  =new ArrayList<>();
		SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMdd");
		String pasta = formataData.format(new Date());
		String imagem;
		
		String caminho = this.apiProperty.getPathRaiz() +"fotos/"+ pasta;
		File novoDiretorio = new File(caminho);
		if (!novoDiretorio.exists()) {
			novoDiretorio.mkdirs();
		}	
		
//		Optional<Retorno> retorno = this.retornoService.buscarRetornoServico(dto.getIdServico());
		for (RetornoFotoDto dto : listaDto) {
			Optional<RetornoFoto> retornoRespostaEncontrado = this.retornoFotoRepository.buscarRetornoFoto(dto.getIdServico(), dto.getNome());
			if(!retornoRespostaEncontrado.isPresent()) {
				RetornoFoto retornoFoto = new RetornoFoto();
				retornoFoto.setNome(dto.getNome());
				retornoFoto.setLatitude(dto.getLatitude());
				retornoFoto.setLongitude(dto.getLongitude());
				retornoFoto.setPath(caminho+"/"+dto.getNome());
				retornoFoto.setServico(new Servico(dto.getIdServico()));
				retornoFoto.setFlagAssinatura(dto.getFlagAssinatura());
				retornoFoto.setDataAtualizacao(dto.getDataAtualizacao());
				retornoFoto.setLongitude(dto.getLongitude());
				retornoFoto.setLatitude(dto.getLatitude());
				retornoFoto.setObservacao(dto.getObservacao());
				
				if(dto.getIdQuestionario() !=null) {
					retornoFoto.setQuestionario(new Questionario(dto.getIdQuestionario()));
				}

				imagem = dto.getImage();
				byte[] image = Base64.decodeBase64(imagem.replace("data:image/jpeg;base64,", ""));
				arrayByteToImage(image,caminho+"/"+dto.getNome());
				
				listaIdServico.add(retornoFoto.getServico().getId());
				listaRetornoFoto.add(retornoFoto);
				
			}
		}
		
		try {
			retornoFotoRepository.saveAll(listaRetornoFoto);
			return listaIdServico;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomRuntimeException("Erro ao Sincronizar!");
		}
	
	}
	
	private void arrayByteToImage(byte[] stream,String pathFull) throws Exception{
		try {
			FileOutputStream tmpImagem = new FileOutputStream(pathFull);
			tmpImagem.write(stream);
			tmpImagem.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CustomRuntimeException("Erro ao salvar foto!");
		}
	
	}
	
	public List<RetornoFotoProjection> getRetornoProjection(Long idServico){
		return this.retornoFotoRepository.getRetornoFoto(idServico);
	}
}
