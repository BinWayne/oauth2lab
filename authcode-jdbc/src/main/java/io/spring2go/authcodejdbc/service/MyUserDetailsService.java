package io.spring2go.authcodejdbc.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.spring2go.authcodejdbc.entity.MyUserPrincipal;
import io.spring2go.authcodejdbc.entity.User;
import io.spring2go.authcodejdbc.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	
	 @Autowired
	    private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByUsername(username);
		
		return new MyUserPrincipal(user);
	}

	
	private User mockUser() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("admin"));
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String pwd = passwordEncoder.encode("123456");
		User user = new User();
		return user;
	}
	
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String pwd = passwordEncoder.encode("secret");
		System.out.println(pwd);
	}
}
