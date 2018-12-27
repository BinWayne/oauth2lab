package io.spring2go.authcodeserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.spring2go.authcodeserver.entity.UserToken;
import io.spring2go.authcodeserver.repo.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Autowired(required = true)
	private UserRepository userRepository;
	
	@Test
	public void test() throws Exception {
		//public UserToken(Long id, String accessToken, Integer refreshToken, String userName, String userPassword) {
		userRepository.save(new UserToken( 1234L, "accessToken","refreshToken","userName","userPassword"));
		userRepository.save(new UserToken( 12345L,  "accessToken1","refreshToken2","userName3","userPassword4"));
	}
	
}
