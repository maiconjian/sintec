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
@Table(name="SERV_RETORNO_FOTO")
public class RetornoFoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name = "NM_NOME")
	private String nome;
	@Column(name = "DS_PATH")
	private String path;
	@ManyToOne
	@JoinColumn(name="ID_SERVICO")
	private Servico servico;
	@ManyToOne
	@JoinColumn(name="ID_QUESTIONARIO")
	private Questionario questionario;
	@Column(name = "CD_LATITUDE")
	private String latitude;
	@Column(name = "CD_LONGITUDE")
	private String longitude;
	@Column(name="FLAG_ASSINATURA")
	private int flagAssinatura;
	@Column(name="FLAG_RACP")
	private int flagRacp;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name="DS_OBSERVACAO")
	private String observacao;
}
