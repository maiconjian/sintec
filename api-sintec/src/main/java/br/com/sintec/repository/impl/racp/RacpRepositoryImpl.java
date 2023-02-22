package br.com.sintec.repository.impl.racp;

import java.time.LocalDate;
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

import br.com.sintec.model.Racp;
import br.com.sintec.model.Racp_;
import br.com.sintec.repository.filter.RacpFilter;

public class RacpRepositoryImpl  implements RacpRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Racp> pesquisar(RacpFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Racp> criteria = builder.createQuery(Racp.class);
		Root<Racp> root = criteria.from(Racp.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Racp_.dataGeracao)));
		
		TypedQuery<Racp> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(RacpFilter filter,CriteriaBuilder builder,Root<Racp> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getCodigoServico())) {
			predicates.add(builder.like(root.join("servico").<String>get("codigo"), filter.getCodigoServico()+"%"));
		}
		
		if(!StringUtils.isEmpty(filter.getCodigoRacp())) {
			predicates.add(builder.like(root.get(Racp_.codigoRacp), filter.getCodigoRacp()+"%"));
		}
		
		if(filter.getIdRegional() > 0) {
			predicates.add(builder.equal(root.join("servico").join("regional").<Long>get("id"), filter.getIdRegional()));
		}
		
		if(filter.getIdTipoServico() > 0) {
			predicates.add(builder.equal(root.join("servico").join("tipoServico").<Long>get("id"), filter.getIdTipoServico()));
		}
		
		
		if(!StringUtils.isEmpty(filter.getDataRef())) {
			LocalDate dataRef = LocalDate.parse(filter.getDataRef());
			predicates.add(builder.equal(root.get(Racp_.dataRef), dataRef));
		}
		
		predicates.add(builder.equal(root.join("situacao").<Long>get("id"),filter.getIdSituacaoRacp()));
		
		return predicates.toArray(new Predicate[predicates.size()]);
		
	}
	
	

}
