package br.com.sintec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.sintec.model.CategoriaVeiculo;

public interface CategoriaVeiculoRepository extends CrudRepository<CategoriaVeiculo, Long> {
	
	
	@Query(value="SELECT * FROM CAD_CATEGORIA_VEICULO WHERE ST_ATIVO = ?1 ",nativeQuery = true)
	public List<CategoriaVeiculo> pesquisar(int ativo);

}
