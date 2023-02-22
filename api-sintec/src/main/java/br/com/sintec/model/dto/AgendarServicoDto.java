package br.com.sintec.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.sintec.model.Colaborador;
import br.com.sintec.model.Imovel;
import br.com.sintec.model.Veiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendarServicoDto {
	
	private List<Colaborador> listaColaborador;
	
	private List<Imovel> listaImovel;
	
	private List<Veiculo> listaVeiculo;
	
	private Integer idCategoriaTipoServico;
	
	private Long idTipoServico;
	
	private Long idRegional;
	
	private int numDiasAgendamento;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate dataSolicitacao;

}
