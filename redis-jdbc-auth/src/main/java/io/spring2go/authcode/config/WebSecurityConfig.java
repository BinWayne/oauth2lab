package io.spring2go.authcode.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private MyUserDetailsService userDetailsService;

	
	@Autowired
	private DataSource dataSource;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		AuthenticationManager authenticationManager = super.authenticationManagerBean();
		return authenticationManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		http.csrf().disable().authorizeRequests().antMatchers("/", "/oauth/**", "/login", "/health", "/css/**","/oauth/authorize")
//				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
		http.csrf().disable().authorizeRequests().antMatchers( "/oauth/**", "/login", "/health", "/css/**","/oauth/authorize")
				.permitAll().anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		 ;
		
//		.csrf().disable() 这个比较重要，否则跳转不到approval 页面
	}
	
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		String pwd = passwordEncoder.encode("123456");
//		manager.createUser(User.withUsername("user_1").password(pwd).authorities("USER").build());
//		manager.createUser(User.withUsername("user_2").password(pwd).authorities("USER").build());
//		return manager;
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(userDetailsService());
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
		
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
