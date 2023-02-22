package br.com.sintec.repository.impl.situacaoracp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sintec.model.SituacaoRacp;
import br.com.sintec.model.SituacaoRacp_;
import br.com.sintec.repository.filter.SituacaoRacpFilter;

public class SituacaoRacpRepositoryImpl implements SituacaoRacpRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<SituacaoRacp> pesquisar(SituacaoRacpFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<SituacaoRacp> criteria = builder.createQuery(SituacaoRacp.class);
		Root<SituacaoRacp> root = criteria.from(SituacaoRacp.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(SituacaoRacp_.id)));
		
		TypedQuery<SituacaoRacp> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(SituacaoRacpFilter filter,CriteriaBuilder builder,Root<SituacaoRacp> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(builder.equal(root.get(SituacaoRacp_.ativo), filter.getAtivo()));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
