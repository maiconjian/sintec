package br.com.sintec.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name="SYS_USUARIO")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "ID")
	private Long id;
	@Column(name = "NM_NOME")
	private String nome;
	@Column(name = "NM_LOGIN")
	private String login;
	@Column(name = "NM_SENHA")
	private String senha;
	@Column(name="NR_MATRICULA")
	private Long matricula;
	@Column(name = "DS_EMAIL")
	private String email;
	@Column(name = "NR_CPF")
	private String cpf;
	@Column(name = "NR_PIN")
	private Long pin;
	@ManyToOne
	@JoinColumn(name="ID_PERFIL_ACESSO")
	private PerfilAcesso perfil;
	
	@ManyToMany
	@JoinTable(name = "USUARIO_REGIONAL",joinColumns = @JoinColumn(name="ID_USUARIO"),inverseJoinColumns = @JoinColumn(name="ID_REGIONAL"))
	private List<Regional> listaRegional;
	
	@Column(name = "DT_ATUALIZACAO")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalDateTime dataAtualizacao;
	@Column(name = "ST_ATIVO")
	private int ativo;
	
	public Usuario() {
		super();
	}

	public Usuario(Long id) {
		super();
		this.id = id;
	}
	
	

//	public Usuario(Long id, String nome, String login, String email, String cpf, PerfilAcesso perfil,
//			List<Regional> listaRegional, int ativo) {
//		super();
//		this.id = id;
//		this.nome = nome;
//		this.login = login;
//		this.email = email;
//		this.cpf = cpf;
//		this.perfil = perfil;
//		this.listaRegional = listaRegional;
//		this.ativo = ativo;
//	}

	
}
