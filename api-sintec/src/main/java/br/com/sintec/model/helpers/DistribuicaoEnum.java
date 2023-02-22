package br.com.sintec.model.helpers;

public enum DistribuicaoEnum {

	DISTRIBUIDO(0),LIBERADO(1),BAIXADO(2);
	
	private final int value;
	
	private DistribuicaoEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

}
