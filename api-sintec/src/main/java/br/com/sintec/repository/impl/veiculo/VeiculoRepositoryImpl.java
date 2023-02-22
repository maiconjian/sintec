package br.com.sintec.repository.impl.veiculo;

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

import br.com.sintec.model.Veiculo;
import br.com.sintec.model.Veiculo_;
import br.com.sintec.repository.filter.VeiculoFilter;

public class VeiculoRepositoryImpl implements VeiculoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Veiculo> pesquisar(VeiculoFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Veiculo> criteria = builder.createQuery(Veiculo.class);
		Root<Veiculo> root = criteria.from(Veiculo.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.desc(root.get(Veiculo_.id)));
		
		TypedQuery<Veiculo> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(VeiculoFilter filter,CriteriaBuilder builder,Root<Veiculo> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdRegional() > 0 ) {
			predicates.add(builder.equal(root.join(Veiculo_.regional).<Long>get("id"), filter.getIdRegional()));
		}
		
		if(!StringUtils.isEmpty(filter.getPlaca())) {
			predicates.add(builder.like(root.get(Veiculo_.placa), filter.getPlaca()+"%"));
		}
		
		if(filter.getIdCategoriaVeiculo() > 0) {
			predicates.add(builder.equal(root.join(Veiculo_.categoriaVeiculo).<Long>get("id"), filter.getIdCategoriaVeiculo()));
		}
		
		predicates.add(builder.equal(root.get(Veiculo_.ativo), filter.getAtivo()));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
