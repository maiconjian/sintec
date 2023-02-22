package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Empresa;
import br.com.sintec.model.helpers.SituacaoEnum;
import br.com.sintec.repository.EmpresaRepository;
import br.com.sintec.repository.filter.EmpresaFilter;
import br.com.sintec.util.Apoio;

@Service
public class EmpresaService implements IGenericService<Empresa, EmpresaFilter, Serializable> {

	@Autowired
	private EmpresaRepository empresaRepository;


	@Override
	public Empresa incluir(Empresa entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		entity.setAtivo(SituacaoEnum.ATIVO.getValue());
		Empresa EmpresaSalvo = this.empresaRepository.save(entity);
		return EmpresaSalvo;
	}

	@Override
	public Empresa alterar(Empresa entity) {
		entity.setDataAtualizacao(Apoio.getDateTimeFuso());
		Empresa EmpresaAlterado = this.empresaRepository.save(entity);
		return EmpresaAlterado;
	}

	@Override
	public Empresa buscarPorId(long id) {
		Optional<Empresa> EmpresaEncontrado = this.empresaRepository.findById(id);
		return EmpresaEncontrado.get();
	}

	@Override
	public List<Empresa> pesquisar(EmpresaFilter filter) {
		return this.empresaRepository.pesquisar(filter.getCnpj(), filter.getNomeFantasia(), filter.getAtivo());
	}

}
