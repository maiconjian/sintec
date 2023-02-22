package br.com.sintec.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Racp;
import br.com.sintec.repository.impl.racp.RacpRepositoryQuery;

public interface RacpRepository extends CrudRepository<Racp, Long>,RacpRepositoryQuery{
	
	@Query(value="SELECT * FROM CAD_RACP AS A "
			   + "INNER JOIN CAD_SERVICO AS B ON B.ID = A.ID_SERVICO "
			   + "WHERE A.ID_SERVICO = ?1 AND B.ST_ATIVO = 1 ",nativeQuery = true)
	public Racp buscarRacp(Long idServico);

}
