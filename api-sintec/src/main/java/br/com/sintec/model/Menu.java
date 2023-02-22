package br.com.sintec.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="CAD_MENU")
public class Menu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name = "NM_NOME")
	private String nome;
	@Column(name = "NM_ICONE")
	private String icone;
	@Column(name = "NM_ROTA")
	private String rota;
	@Column(name = "ID_MENU")
	private Long idMenuPai;
	@Column(name = "NR_ORDEM_APRESENTACAO")
	private Integer ordemApresentacao;
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name = "ST_ATIVO")
	private int ativo;


}
