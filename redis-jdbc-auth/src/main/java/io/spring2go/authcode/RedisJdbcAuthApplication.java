package io.spring2go.authcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"io.spring2go"})
public class RedisJdbcAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisJdbcAuthApplication.class, args);
	}

}

