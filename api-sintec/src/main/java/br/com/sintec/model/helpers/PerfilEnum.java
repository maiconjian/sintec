package br.com.sintec.model.helpers;

public enum PerfilEnum {
	
   DESENVOLVEDOR((long) 1),GESTOR((long) 2),USUARIO((long) 3),MOBILE((long) 4);
	
	private final Long value;
	
	private PerfilEnum(Long value) {
		this.value = value;
	}
	
	public Long getValue() {
		return value;
	}

}
