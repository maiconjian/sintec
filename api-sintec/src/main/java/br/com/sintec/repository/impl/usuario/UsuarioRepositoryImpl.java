package br.com.sintec.repository.impl.usuario;

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

import br.com.sintec.model.PerfilAcesso;
import br.com.sintec.model.Regional;
import br.com.sintec.model.Usuario;
import br.com.sintec.model.Usuario_;
import br.com.sintec.repository.filter.UsuarioFilter;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Usuario> pesquisar(UsuarioFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

			
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
//		criteria.multiselect(
//		root.get(Usuario_.id),
//		root.get(Usuario_.nome),
//		root.get(Usuario_.login),
//		root.get(Usuario_.email),
//		root.get(Usuario_.cpf),
//		root.get(Usuario_.perfil),
//		root.get(Usuario_.listaRegional),
//		root.get(Usuario_.ativo)
//		);		
//		
		
		criteria.orderBy(builder.desc(root.get(Usuario_.id)));
		
		TypedQuery<Usuario> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	
	private Predicate[] criarRestricoes(UsuarioFilter filter,CriteriaBuilder builder,Root<Usuario>root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(root.get(Usuario_.nome), filter.getNome()+"%"));
		}
		
		if(filter.getIdRegional() > 0) {
			predicates.add(builder.equal(root.join("listaRegional").<Regional>get("id"), filter.getIdRegional()));
		}
		
		if(filter.getIdPerfil() > 0) {
			predicates.add(builder.equal(root.join("perfil").<PerfilAcesso>get("id"), filter.getIdPerfil()));
		}
		
		if(filter.getAtivo() < 2) {
			predicates.add(builder.equal(root.get(Usuario_.ativo), filter.getAtivo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
		
		
		
	}

}
