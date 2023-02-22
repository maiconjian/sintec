package br.com.sintec.resource;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;

public interface IGenericResource<T,F,id extends Serializable> {
	
	public ResponseEntity<?> incluir(T entity) throws Exception;
	
	public ResponseEntity<?> alterar(T entity) throws Exception;
	
	public ResponseEntity<?> buscarPorId(long id);
	
	public ResponseEntity<?> pesquisar(F filter);
	

	
}