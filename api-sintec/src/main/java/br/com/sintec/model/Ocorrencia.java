package br.com.sintec.model;

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
@Table(name="CAD_OCORRENCIA")
public class Ocorrencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name="DS_DESCRICAO")
	private String descricao;
	@ManyToOne
	@JoinColumn(name="ID_CONTRATO")
	private Contrato contrato;
	@Column(name = "ST_ATIVO")
	private int ativo;
}
