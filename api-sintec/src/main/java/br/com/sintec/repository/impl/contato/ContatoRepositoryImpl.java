package br.com.sintec.repository.impl.contato;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sintec.model.Contato;
import br.com.sintec.model.Contato_;
import br.com.sintec.repository.filter.ContatoFilter;

public class ContatoRepositoryImpl implements ContatoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Contato> pesquisar(ContatoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Contato> criteria = builder.createQuery(Contato.class);
		Root<Contato> root = criteria.from(Contato.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Contato_.id)));
		
		TypedQuery<Contato> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(ContatoFilter filter,CriteriaBuilder builder,Root<Contato> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdRegional() > 0 ) {
			predicates.add(builder.equal(root.join("regional").<Long>get("id"), filter.getIdRegional()));
		}
		
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Contato_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	
}
