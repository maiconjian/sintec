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
@Table(name="CAD_ALTERNATIVA")
public class Alternativa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_PERGUNTA")
	private Pergunta pergunta;
	@Column(name="NM_DESCRICAO")
	private String descricao;
	@Column(name = "FLAG_NCONF")
	private int flagNconf;
	@Column(name = "NR_NCONF_PRIORIDADE")
	private int prioridadeNconf;
	@Column(name = "DS_NCONF_RECOMENDACAO")
	private String recomendacaoNconf;
	@Column(name = "NR_ORDEM_APRESENTACAO")
	private int ordemApresentacao;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name = "ST_ATIVO")
	private int ativo;
	
	public Alternativa(Long id) {
		super();
		this.id = id;
	}

	public Alternativa() {
		super();
	}
}
