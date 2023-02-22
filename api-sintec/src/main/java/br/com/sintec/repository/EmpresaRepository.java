package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
	
	
	@Query(value="SELECT * FROM CAD_EMPRESA "
			+ "WHERE (NR_CNPJ = ?1 OR ?1 = 'null') "
			+ "AND (NM_FANTASIA LIKE CONCAT(?2,'%') OR ?2 = 'null') "
			+ "AND (ST_ATIVO = ?3 OR ?3 = 2)",nativeQuery = true)
	public List<Empresa> pesquisar(String cnpj,String nomeFantasia,int ativo);
}
