package br.com.sintec.model;

import java.time.LocalDate;
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
@Table(name="CAD_VEICULO")
public class Veiculo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name = "NM_PLACA")
	private String placa;
	@Column(name = "NM_COR")
	private String cor;
	@Column(name = "NR_ANO")
	private Integer ano;
	@Column(name = "NM_MODELO")
	private String modelo;
	@ManyToOne
	@JoinColumn(name="ID_REGIONAL")
	private Regional regional;
	@ManyToOne
	@JoinColumn(name="ID_COLABORADOR")
	private Colaborador colaborador;
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA_VEICULO")
	private CategoriaVeiculo categoriaVeiculo;
	@Column(name="DT_VENCIMENTO_DOC")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate dataVencimentoDoc;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name = "ST_ATIVO")
	private int ativo;

}
