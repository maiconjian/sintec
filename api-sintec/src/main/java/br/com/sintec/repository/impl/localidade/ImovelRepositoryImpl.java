package br.com.sintec.repository.impl.localidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sintec.model.Imovel;
import br.com.sintec.model.Imovel_;
import br.com.sintec.repository.filter.ImovelFilter;
 
public class ImovelRepositoryImpl implements ImovelRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Imovel> pesquisar(ImovelFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Imovel> criteria = builder.createQuery(Imovel.class);
		Root<Imovel> root = criteria.from(Imovel.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Imovel_.id)));
		
		TypedQuery<Imovel> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(ImovelFilter filter,CriteriaBuilder builder,Root<Imovel> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdRegional() > 0 ) {
			predicates.add(builder.equal(root.join("regional").<Long>get("id"), filter.getIdRegional()));
		}
		
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Imovel_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
