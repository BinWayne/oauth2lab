package io.spring2go.authcodeserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import io.spring2go.authcodejdbc.service.MyUserDetailsService;

//授权服务器配置
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String secret = passwordEncoder.encode("secret");

		clients.inMemory()
			.withClient("client")
			.secret(secret)
			//.redirectUris("http://localhost:8081/oauth/login/client-app")
			.redirectUris("http://baidu.com")
				// 授权码模式
			.authorizedGrantTypes("authorization_code","refresh_token")
			.scopes("read_userinfo", "read_contacts","app");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// TODO Auto-generated method stub
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		endpoints.authenticationManager(authenticationManager)
		//.userDetailsService(userDetailsService)
		.accessTokenConverter(accessTokenConverter())
		.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
		//authenticationManager 通过这个认证
		//userDetailsService 通过自定义service 查找user
		//accessTokenConverter 通过这个返回token 变成jwt
		//access token 可以是 GET POST 请求获取
	}

	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
		
	/****  非对称 加密 *****/
	/**
	 * 
	 JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				String userName = authentication.getUserAuthentication().getName();
	            final Map<String, Object> additionalInformation = new HashMap<String, Object>();
	            additionalInformation.put("user_name", userName);
	            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
	            OAuth2AccessToken token = super.enhance(accessToken, authentication);
	            return token;
			}
			

		};
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("kevin_key.jks"), "123456".toCharArray())
                .getKeyPair("kevin_key");
        converter.setKeyPair(keyPair);

	 * 
	 * */
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter	();
		converter.setSigningKey("123");
		
       
        return converter;

		
		
	}
}
