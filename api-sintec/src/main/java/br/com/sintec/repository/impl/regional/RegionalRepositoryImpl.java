package br.com.sintec.repository.impl.regional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.sintec.model.Regional;
import br.com.sintec.model.Regional_;
import br.com.sintec.repository.filter.RegionalFilter;

public class RegionalRepositoryImpl implements RegionalRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Regional> pesquisar(RegionalFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Regional> criteria = builder.createQuery(Regional.class);
		Root<Regional> root = criteria.from(Regional.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Regional_.id)));
		
		TypedQuery<Regional> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(RegionalFilter filter,CriteriaBuilder builder,Root<Regional> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdContrato() > 0 ) {
			predicates.add(builder.equal(root.join("contrato").<Long>get("id"), filter.getIdContrato()));
		}
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(root.get(Regional_.nome), filter.getNome()+"%"));
		}
		
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Regional_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
