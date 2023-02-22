package br.com.sintec.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmacaoDownloadServicoDto {
	
	private List<Long> listaIdServico;
	private Long idUsuario;

}
