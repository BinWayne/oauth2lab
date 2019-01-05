package io.spring2go.authcodeserver.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

//授权服务器配置
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private  DataSource dataSource;


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.jdbc(dataSource);
	}
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// TODO Auto-generated method stub
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		
	}

	 @Bean
	   UserApprovalHandler userApprovalHandler() {
	      return new DefaultUserApprovalHandler();
	   }
	 
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		// TODO Auto-generated method stub
//		endpoints.authenticationManager(authenticationManager)
//		//.userDetailsService(userDetailsService)
//		.accessTokenConverter(accessTokenConverter())
//		.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//		//authenticationManager 通过这个认证
//		//userDetailsService 通过自定义service 查找user
//		//accessTokenConverter 通过这个返回token 变成jwt
//		//access token 可以是 GET POST 请求获取
//	}
	
	
	@Bean
	public JdbcApprovalStore approvalStore() {
	return new JdbcApprovalStore(dataSource);
	}
	
	 @Bean  
	    protected AuthorizationCodeServices authorizationCodeServices() {  
	        return new JdbcAuthorizationCodeServices(dataSource);  
	    }  
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
        endpoints.tokenStore(tokenStore())  // oauth_access_token & oauth_refresh_token  
                 .accessTokenConverter(accessTokenConverter())
                 .authenticationManager(authenticationManager)
                 .approvalStore(approvalStore()) // oauth_approvals
                 .authorizationCodeServices(authorizationCodeServices())
                // .userApprovalHandler(userApprovalHandler())
                // .tokenServices(tokenServices())
                 //如果不指定tokenService，默认会使用 default token service
                 //生成的token 是jwt，否则 tokenServices() 根据这个方法生成service，但是不生成jwt
                 // 除非指定token enhancer
                 .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }


//	@Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }

	@Bean  
    public TokenStore tokenStore() {  
        return new JdbcTokenStore(dataSource);  
    }  
	
//	 @Bean
//	 public TokenStore tokenStore() {
//	       return new JdbcTokenStore(dataSource);
//	 }
	
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
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter	() {
			@Override
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
		converter.setSigningKey("123");
        return converter;
	}
	
	@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        
        return defaultTokenServices;
    }
}
