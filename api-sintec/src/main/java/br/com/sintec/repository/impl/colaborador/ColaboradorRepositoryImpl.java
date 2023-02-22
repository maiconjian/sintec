package br.com.sintec.repository.impl.colaborador;

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

import br.com.sintec.model.Colaborador;
import br.com.sintec.model.Colaborador_;
import br.com.sintec.repository.filter.ColaboradorFilter;

public class ColaboradorRepositoryImpl implements ColaboradorRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Colaborador> pesquisar(ColaboradorFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Colaborador> criteria = builder.createQuery(Colaborador.class);
		Root<Colaborador> root = criteria.from(Colaborador.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Colaborador_.id)));
		
		TypedQuery<Colaborador> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(ColaboradorFilter filter,CriteriaBuilder builder,Root<Colaborador> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdRegional() > 0 ) {
			predicates.add(builder.equal(root.join(Colaborador_.regional).<Long>get("id"), filter.getIdRegional()));
		}
		
		if(filter.getMatricula() > 0 ) {
			predicates.add(builder.equal(root.get(Colaborador_.matricula),filter.getMatricula()));
		}
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(root.get(Colaborador_.nome), filter.getNome()+"%"));
		}
		
	
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Colaborador_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
