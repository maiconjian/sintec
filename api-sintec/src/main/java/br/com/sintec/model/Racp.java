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
@Table(name="CAD_RACP")
public class Racp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_SERVICO")
	private Servico servico;
	@ManyToOne
	@JoinColumn(name="ID_SITUACAO")
	private SituacaoRacp situacao;
	@Column(name = "DT_REF")
	private LocalDate dataRef;
	@Column(name = "CD_RACP")
	private String codigoRacp;
	@Column(name = "NR_PRIORIDADE")
	private int prioridade;
	@Column(name = "DT_GERACAO")
	private LocalDate dataGeracao;
	@Column(name = "DT_PROGRAMADA")
	private LocalDate dataProgramada;
	@Column(name = "DT_EXECUCAO")
	private LocalDate dataExecucao;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;

}
