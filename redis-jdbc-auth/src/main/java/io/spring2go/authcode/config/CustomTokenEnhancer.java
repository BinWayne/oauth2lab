package io.spring2go.authcode.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomTokenEnhancer implements TokenEnhancer{

	 @Autowired
	    private JwtAccessTokenConverter jwtAccessTokenConverter;
	 
	 /**
	  * @Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				User user =(User) authentication.getUserAuthentication().getPrincipal();
	            final Map<String, Object> additionalInformation = new HashMap<String, Object>();
//	            additionalInformation.put("user_name", user.getUsername());
//	            additionalInformation.put("user_authorities",user.getAuthorities());
	            additionalInformation.put("userDetails", user);
	            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
	            OAuth2AccessToken token = super.enhance(accessToken, authentication);
	            return token;
			}
		};
	  * */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		
		User user =(User) authentication.getUserAuthentication().getPrincipal();
        final Map<String, Object> additionalInformation = new HashMap<String, Object>();
//        additionalInformation.put("user_name", user.getUsername());
//        additionalInformation.put("user_authorities",user.getAuthorities());
        additionalInformation.put("userDetails", user);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        OAuth2AccessToken token = jwtAccessTokenConverter.enhance(accessToken, authentication);
		return token;
	}

	
	

	
}
