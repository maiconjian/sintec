package br.com.sintec.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Contato;
import br.com.sintec.repository.impl.contato.ContatoRepositoryQuery;

public interface ContatoRepository extends CrudRepository<Contato, Long>,ContatoRepositoryQuery{

	
	@Transactional
	@Modifying
	@Query(value = "DELETE CAD_CONTATO WHERE ID = ?1 ",nativeQuery = true)
	public void deletar(Long id);
}
