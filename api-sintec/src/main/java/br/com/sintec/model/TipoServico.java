package br.com.sintec.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="CAD_TIPO_SERVICO")
public class TipoServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name = "CD_CODIGO")
	private String codigo;
	@Column(name = "NM_NOME")
	private String nome;
	@Column(name="NR_DIAS_AGENDAMENTO")
	private int numDiasAgendamento;
	@ManyToOne
	@JoinColumn(name="ID_CONTRATO")
	private Contrato contrato;
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA_TIPO_SERVICO")
	private CategoriaTipoServico categoriaTipoServico;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name = "ST_ATIVO")
	private int ativo;
	
}
