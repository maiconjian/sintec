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
@Table(name="CAD_SERVICO")
public class Servico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name="CD_SERVICO")
	private String codigo;
	@Column(name="DT_REF")
	private LocalDate dataRef;
	@ManyToOne
	@JoinColumn(name="ID_TIPO_SERVICO")
	private TipoServico tipoServico;
	@ManyToOne
	@JoinColumn(name="ID_IMOVEL")
	private Imovel imovel;
	@ManyToOne
	@JoinColumn(name="ID_VEICULO")
	private Veiculo veiculo;
	@ManyToOne
	@JoinColumn(name="ID_COLABORADOR")
	private Colaborador	colaborador;
	@Column(name="DT_SOLICITACAO")
	private LocalDate dataSolicitacao;
	@Column(name="NR_DIAS_AGENDAMENTO")
	private int numDiasAgendamento;
	@Column(name="DT_PROGRAMADA")
	private LocalDate dataProgramada;
	@Column(name="FLAG_AVULSA")
	private int flagAvulsa;
	@ManyToOne
	@JoinColumn(name="ID_REGIONAL")
	private Regional regional;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name="ST_ATIVO")
	private int ativo;
	
	public Servico() {
		super();
	}

	public Servico(Long id) {
		super();
		this.id = id;
	}
	
	
	
	

}
