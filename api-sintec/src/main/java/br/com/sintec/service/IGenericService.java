package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;

public interface IGenericService<T, F, id extends Serializable> {

	public T incluir(T entity) throws Exception;

	public T alterar(T entity);

	public T buscarPorId(long id);

	public List<T> pesquisar(F filter);
	
}
