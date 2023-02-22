package br.com.sintec.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.SituacaoRacp;
import br.com.sintec.repository.impl.situacaoracp.SituacaoRacpRepositoryQuery;

public interface SituacaoRacpRepository extends CrudRepository<SituacaoRacp, Long>,SituacaoRacpRepositoryQuery {

}
