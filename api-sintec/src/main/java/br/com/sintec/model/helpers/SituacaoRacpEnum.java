package br.com.sintec.model.helpers;

public enum SituacaoRacpEnum {
	
	PENDENTE(1),CONCLUIDO(2),CANCELADO(3);
	
	private final long value;
	
	private SituacaoRacpEnum(long value) {
		this.value = value;
	}
	
	public long getValue() {
		return value;
	}

}
