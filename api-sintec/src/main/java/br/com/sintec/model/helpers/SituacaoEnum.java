package br.com.sintec.model.helpers;

public enum SituacaoEnum {
	
	ATIVO(1),INATIVO(0);
	
	private final int value;
	
	private SituacaoEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

}
