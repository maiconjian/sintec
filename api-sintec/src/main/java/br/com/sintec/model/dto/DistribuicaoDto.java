package br.com.sintec.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistribuicaoDto {
	
	private Long idUsuario;
	private List<Long> listaIdServico;
}
