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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="SERV_RETORNO")
public class Retorno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name="ID_SERVICO")
	private Servico servico;
	@ManyToOne
	@JoinColumn(name="ID_OCORRENCIA")
	private Ocorrencia ocorrencia;
	@Column(name = "DT_EXECUCAO")
	private LocalDateTime dataExecucao;
	@Column(name = "CD_LATITUDE")
	private String latitude;
	@Column(name = "CD_LONGITUDE")
	private String longitude;
	@Column(name="NM_MODELO_APARELHO")
	private String modeloAparelho;
	@Column(name="NM_MARCA_APARELHO")
	private String marcaAparelho;
	@Column(name="FLAG_RACP")
	private int flagRacp;
	@Column(name = "ST_ATIVO")
	private int ativo;
}
