package br.com.sintec.repository.impl.contrato;

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

import br.com.sintec.model.Contrato;
import br.com.sintec.model.Contrato_;
import br.com.sintec.repository.filter.ContratoFilter;

public class ContratoRepositoryImpl implements ContratoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Contrato> pesquisar(ContratoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Contrato> criteria = builder.createQuery(Contrato.class);
		Root<Contrato> root = criteria.from(Contrato.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Contrato_.id)));
		
		TypedQuery<Contrato> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(ContratoFilter filter,CriteriaBuilder builder,Root<Contrato> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdEmpresa() > 0 ) {
			predicates.add(builder.equal(root.join("empresa").<Long>get("id"), filter.getIdEmpresa()));
		}
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(root.get(Contrato_.nome), filter.getNome()+"%"));
		}
		
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Contrato_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
