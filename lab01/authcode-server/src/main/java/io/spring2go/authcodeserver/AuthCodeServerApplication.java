package io.spring2go.authcodeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"io.spring2go.authcodeserver"})
public class AuthCodeServerApplication {

	//若是普通Database 需要uncomment，若是druid ，则全部注释
//	 	@Bean
//	    @ConfigurationProperties(prefix = "db")
//	    public DataSource dateSource() {
//	        DruidDataSource dataSource = new DruidDataSource();
//	        return dataSource;
//	    }
	 
	 
	public static void main(String[] args) {
		SpringApplication.run(AuthCodeServerApplication.class, args);
	}
}
