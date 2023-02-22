package br.com.sintec.model.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetornoDto {
	
	private Long id;
	private Long idUsuario;
	private Long idServico;	
	private Long idOcorrencia;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataExecucao;
	private String latitude;
	private String longitude;
	String modeloAparelho;
	String marcaAparelho;
	private int flagRacp;
	private int sincronizado;
	private int ativo;

}
