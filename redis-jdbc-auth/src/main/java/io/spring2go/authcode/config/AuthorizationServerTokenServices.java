package io.spring2go.authcode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

public class AuthorizationServerTokenServices extends DefaultTokenServices{

	@Autowired
	private RedisTemplate redisTemplate;
	@Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
       //later, we could use redis to get the access token, if not existing, then create
		redisTemplate.opsForValue().set("name","tom");
		return super.createAccessToken(authentication);
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest) throws AuthenticationException {
        //later, we could use redis to refresh the access token
        return super.refreshAccessToken(refreshTokenValue, tokenRequest);
    }
    
    @Override
    public void setTokenStore(TokenStore tokenStore) {
        super.setTokenStore(tokenStore);
    }

}
