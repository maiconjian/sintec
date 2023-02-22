package br.com.sintec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.sintec.config.ApiProperty;

@SpringBootApplication(exclude={UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties(ApiProperty.class)
public class ApiSintecApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSintecApplication.class, args);
	}

}
