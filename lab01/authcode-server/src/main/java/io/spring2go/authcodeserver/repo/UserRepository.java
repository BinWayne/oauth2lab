package io.spring2go.authcodeserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.spring2go.authcodeserver.entity.UserToken;

@Repository
public interface UserRepository extends JpaRepository<UserToken, Long>{

//	UserToken findByName(String username);
//
//    @Query("from UserToken u where u.username=:username")
//    UserToken findUser(@Param("username") String username);
}
