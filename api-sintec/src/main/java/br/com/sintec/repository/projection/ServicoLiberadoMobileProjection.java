package br.com.sintec.repository.projection;

import java.time.LocalDate;

public interface ServicoLiberadoMobileProjection {
	
	Long getId();
	Long getCodigo();
	LocalDate getDataRef();
	Long getIdContrato();
	Long getIdRegional();
	String getNomeRegional();
	Long getIdCategoriaTipoServico();
	String getNomeCategoriaTipoServico();
	Long getIdTipoServico();
	String getNomeTipoServico();
	Long getIdImovel();
	String getNomeImovel();
	String getBairro();
	String getLogradouro();
	Long getNumeroLogradouro();
	Long getIdVeiculo();
	String getPlaca();
	String getCor();
	String getModelo();
	Long getIdColaborador();
	String getNomeColaborador();
	String getCnh();
	LocalDate getValidadeCnh();
	String getCategoriaCnh();
	LocalDate getDataProgramada();
	Long getIdUsuario();

}
