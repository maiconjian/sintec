package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Ocorrencia;
import br.com.sintec.repository.OcorrenciaRepository;
import br.com.sintec.repository.filter.OcorrenciaFilter;
import br.com.sintec.repository.projection.OcorrenciaMobileProjection;
@Service
public class OcorrenciaService implements IGenericService<Ocorrencia, OcorrenciaFilter, Serializable> {

	@Autowired
	private OcorrenciaRepository ocorrenciaRepository;
	
	
	@Override
	public Ocorrencia incluir(Ocorrencia entity) {
		Ocorrencia OcorrenciaSalvo = this.ocorrenciaRepository.save(entity);
		return OcorrenciaSalvo;
	}

	@Override
	public Ocorrencia alterar(Ocorrencia entity) {
		Ocorrencia OcorrenciaAlterado = this.ocorrenciaRepository.save(entity);
		return OcorrenciaAlterado;
	}

	@Override
	public Ocorrencia buscarPorId(long id) {
		Optional<Ocorrencia> OcorrenciaEncontrado = this.ocorrenciaRepository.findById(id);
		return OcorrenciaEncontrado.get();
	}

	@Override
	public List<Ocorrencia> pesquisar(OcorrenciaFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<OcorrenciaMobileProjection> getOcorrenciaMovile(Long idUsuario){
		return this.ocorrenciaRepository.getOcorrenciaMobile(idUsuario);
	}

}
