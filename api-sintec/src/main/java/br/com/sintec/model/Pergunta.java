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
@Table(name="CAD_PERGUNTA")
public class Pergunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name = "NM_ENUNCIADO")
	private String enunciado;
	@Column(name = "NR_ORDEM_APRESENTACAO")
	private int ordemApresentacao;
	@Column(name = "FLAG_FOTO")
	private int flagFoto;
	@Column(name = "FLAG_OBRIGATORIO")
	private int flagObrigatorio;
	@ManyToOne
	@JoinColumn(name="ID_QUESTIONARIO")
	private Questionario questionario;
	
	@Column(name="FLAG_MULTIPLA_ESCOLHA")
	private int flagMultiplaEscolha;
	
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name = "ST_ATIVO")
	private int ativo;
	
	public Pergunta(Long id) {
		super();
		this.id = id;
	}

	public Pergunta() {
		super();
	}
	
	
	
	
}
