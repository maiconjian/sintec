package br.com.sintec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("sintec")
public class ApiProperty {
	

	private String originPermitida = "http://localhost";

	private String urlDesenv = "http://localhost:4200";

	private String urlMobile = "http://localhost";

	private String urlProducao = "http://localhost:4200";

	private String urlHomologacao = "http://localhost:4200";

	
//	private String pathRaiz = "/opt/jboss/wildfly/standalone/tmp/sintec/";
	
	private String pathRaiz  = "C:/opt/jboss/wildfly/standalone/tmp/gfsintec";


	private final Seguranca seguranca = new Seguranca();
	private final Mail mail = new Mail();

	
	
	public static class Seguranca {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
	
	@Getter
	@Setter
	public static class Mail{
		
		private String host;
		
		private Integer port;
		
		private String username;
		
		private String password;
		
		
	}

}
