package io.spring2go.authcode.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.xml.bind.v2.model.core.ID;

import io.spring2go.authcode.entity.User;

public interface UserRepository extends JpaRepository<User, ID>{

	User findByUsername(String username);
	
}
