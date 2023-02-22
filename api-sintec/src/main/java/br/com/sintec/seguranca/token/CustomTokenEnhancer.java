package br.com.sintec.seguranca.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.sintec.repository.projection.ContratoUsuarioProjection;
import br.com.sintec.repository.projection.RegionalUsuarioProjection;
import br.com.sintec.seguranca.UsuarioSistema;
import br.com.sintec.service.ContratoService;
import br.com.sintec.service.RegionalService;

public class CustomTokenEnhancer implements TokenEnhancer {

//	@Autowired
//	private UsuarioService usuarioService;
	
	@Autowired
	private RegionalService regionalService;
	@Autowired
	private ContratoService contratoService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		List<RegionalUsuarioProjection> listaRegionalUsuario = this.regionalService.listaRegionalUsuario(usuarioSistema.getUsuario().getId());
		List<ContratoUsuarioProjection> listaContratoUsuario = this.contratoService.listaContratoUsuario(usuarioSistema.getUsuario().getId());
		
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("id", usuarioSistema.getUsuario().getId());
		addInfo.put("nome", usuarioSistema.getUsuario().getNome());
		addInfo.put("idPerfil", usuarioSistema.getUsuario().getPerfil().getId());
		addInfo.put("nomePerfil", usuarioSistema.getUsuario().getPerfil().getNome());
		addInfo.put("email", usuarioSistema.getUsuario().getEmail());
		addInfo.put("listaRegional",listaRegionalUsuario);
		addInfo.put("listaContrato", listaContratoUsuario);
		

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}



}
