package br.com.sintec.repository.impl.tiposervico;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sintec.model.TipoServico;
import br.com.sintec.model.TipoServico_;
import br.com.sintec.repository.filter.TipoServicoFilter;

public class TipoServicoRepositoryImpl implements TipoServicoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<TipoServico> pesquisar(TipoServicoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TipoServico> criteria = builder.createQuery(TipoServico.class);
		Root<TipoServico> root = criteria.from(TipoServico.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(TipoServico_.id)));
		
		TypedQuery<TipoServico> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(TipoServicoFilter filter,CriteriaBuilder builder,Root<TipoServico> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdContrato() > 0 ) {
			predicates.add(builder.equal(root.join("contrato").<Long>get("id"), filter.getIdContrato()));
		}
		
		if(filter.getIdCategoria() > 0) {
			predicates.add(builder.equal(root.join(TipoServico_.categoriaTipoServico).<Long>get("id"),filter.getIdCategoria()));
		}
		
		predicates.add(builder.equal(root.get(TipoServico_.ativo), filter.getAtivo()));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
