package br.com.sintec.repository.impl.questionario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sintec.model.Questionario;
import br.com.sintec.model.Questionario_;
import br.com.sintec.repository.filter.QuestionarioFilter;

public class QuestionarioRepositoryImpl implements QuestionarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Questionario> pesquisar(QuestionarioFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Questionario> criteria = builder.createQuery(Questionario.class);
		Root<Questionario> root = criteria.from(Questionario.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		
		criteria.where(predicates);
		criteria.orderBy(builder.asc(root.get(Questionario_.ordemApresentacao)));
		
		TypedQuery<Questionario> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(QuestionarioFilter filter,CriteriaBuilder builder,Root<Questionario> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getIdContrato() > 0 ) {
			predicates.add(builder.equal(root.join("tipoServico").join("contrato").<Long>get("id"), filter.getIdContrato()));
		}
		
		if(filter.getIdTipoServico() > 0 ) {
			predicates.add(builder.equal(root.join("tipoServico").<Long>get("id"), filter.getIdTipoServico()));
		}
		
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Questionario_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
