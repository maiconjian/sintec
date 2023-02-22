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
@Table(name="CAD_COLABORADOR")
public class Colaborador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name="NM_NOME")
	private String nome;
	@Column(name="NR_MATRICULA")
	private Long matricula;
	@Column(name="NR_CPF")
	private String cpf;
	@Column(name="DT_NASCIMENTO")
	private LocalDate dataNascimento;
	@Column(name="DT_ADMISSAO")
	private LocalDate dataAdmissao;
	@Column(name="NR_CNH")
	private String cnh;
	@Column(name="DT_VALIDADE_CNH")
	private LocalDate dataValidadeCnh;
	@Column(name="DS_CATEGORIA_CNH")
	private String categoriaCnh;
	@ManyToOne
	@JoinColumn(name="ID_REGIONAL")
	private Regional regional;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name="ST_ATIVO")
	private int ativo;

}
