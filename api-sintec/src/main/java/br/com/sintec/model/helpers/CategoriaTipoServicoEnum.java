package br.com.sintec.model.helpers;

public enum CategoriaTipoServicoEnum {
	
	VEICULO(1),LOCAL(2),FUNCIONARIO(3);
	
	private final int value;
	
	private CategoriaTipoServicoEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}