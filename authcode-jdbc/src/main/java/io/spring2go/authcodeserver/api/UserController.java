package io.spring2go.authcodeserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring2go.authcodejdbc.entity.MyUserPrincipal;
import io.spring2go.authcodejdbc.entity.User;
import io.spring2go.authcodejdbc.service.MyUserDetailsService;

@RestController
public class UserController {

	@Autowired
	private MyUserDetailsService userDetailsService;
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

    @RequestMapping("/api/userdetails")
    public String getUserDetailServiceInfo() {
    	MyUserPrincipal userInfo =(MyUserPrincipal) userDetailsService.loadUserByUsername("user_1");
        	
        return userInfo.getPassword();
    }
    
}