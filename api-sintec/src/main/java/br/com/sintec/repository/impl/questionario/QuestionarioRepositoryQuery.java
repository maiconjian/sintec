package br.com.sintec.repository.impl.questionario;

import java.util.List;

import br.com.sintec.model.Questionario;
import br.com.sintec.repository.filter.QuestionarioFilter;

public interface QuestionarioRepositoryQuery {
	
	
	public List<Questionario> pesquisar(QuestionarioFilter filter);

}
