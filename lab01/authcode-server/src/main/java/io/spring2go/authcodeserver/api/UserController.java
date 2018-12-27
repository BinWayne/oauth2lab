package io.spring2go.authcodeserver.api;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.pool.DruidDataSource;

@Controller
public class UserController {

	  @Autowired
	    private DataSource dataSource;

	    @RequestMapping("/index")
	    @ResponseBody
	    public String index() throws SQLException {
	        System.out.println(dataSource.getConnection());
	        if(dataSource instanceof DruidDataSource) {
	        	System.out.println("true");
	        	DruidDataSource dd = (DruidDataSource)dataSource;
	        	dd.getConnection();
	        	 System.out.println(dd);
	        }
	       
	        return "hello spring boot";
	    }
	    
	// 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<UserInfo> getUserInfo() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = user.getUsername() + "@spring2go.com";

        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getUsername());
        userInfo.setEmail(email);

        return ResponseEntity.ok(userInfo);
    }

    
}