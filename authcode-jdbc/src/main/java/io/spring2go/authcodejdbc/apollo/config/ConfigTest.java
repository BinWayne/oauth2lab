package io.spring2go.authcodejdbc.apollo.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

@RestController
public class ConfigTest {

	@ApolloConfig
	private Config config; 
	
	@GetMapping("/apollo/hello")
	public String hello() {
		String value = config.getProperty("timeout", "10sdfsdfsd2");
		return value;
	}
}
