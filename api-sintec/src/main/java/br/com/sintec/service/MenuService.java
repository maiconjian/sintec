package br.com.sintec.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sintec.model.Menu;
import br.com.sintec.repository.MenuRepository;
import br.com.sintec.repository.filter.MenuFilter;

@Service
public class MenuService implements IGenericService<Menu, MenuFilter, Serializable>{

	@Autowired
	private MenuRepository menuRepository;
	
	
	@Override
	public Menu incluir(Menu entity) {
		Menu menuSalvo = this.menuRepository.save(entity);
		return menuSalvo;
	}

	@Override
	public Menu alterar(Menu entity) {
		Menu menuAlterado = this.menuRepository.save(entity);
		return menuAlterado;
	}

	@Override
	public Menu buscarPorId(long id) {
		Optional<Menu> menuEncontrado = this.menuRepository.findById(id);
		return menuEncontrado.get();
	}

	@Override
	public List<Menu> pesquisar(MenuFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
