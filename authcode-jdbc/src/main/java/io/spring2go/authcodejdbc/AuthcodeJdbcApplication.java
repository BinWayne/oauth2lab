package io.spring2go.authcodejdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringBootApplication
@EnableApolloConfig
@ComponentScan({"io.spring2go"})
public class AuthcodeJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthcodeJdbcApplication.class, args);
	}

}

