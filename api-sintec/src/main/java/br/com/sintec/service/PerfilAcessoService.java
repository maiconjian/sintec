package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.PerfilAcesso;
import br.com.sintec.repository.PerfilAcessoRepository;
import br.com.sintec.repository.filter.PerfilAcessoFilter;

@Service
public class PerfilAcessoService implements IGenericService<PerfilAcesso, PerfilAcessoFilter, Serializable>{

	@Autowired
	private PerfilAcessoRepository perfilAcessoRepository;

	
	@Override
	public PerfilAcesso incluir(PerfilAcesso entity) {
		PerfilAcesso PerfilAcessoSalvo = this.perfilAcessoRepository.save(entity);
		return PerfilAcessoSalvo;
	}

	@Override
	public PerfilAcesso alterar(PerfilAcesso entity) {
		PerfilAcesso PerfilAcessoAlterado = this.perfilAcessoRepository.save(entity);
		return PerfilAcessoAlterado;
	}

	@Override
	public PerfilAcesso buscarPorId(long id) {
		Optional<PerfilAcesso> PerfilAcessoEncontrado = this.perfilAcessoRepository.findById(id);
		return PerfilAcessoEncontrado.get();
	}

	@Override
	public List<PerfilAcesso> pesquisar(PerfilAcessoFilter filter) {
		return this.perfilAcessoRepository.pesquisar(filter.getAtivo());
	
	}
	
}
