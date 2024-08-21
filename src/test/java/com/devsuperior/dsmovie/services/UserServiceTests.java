package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.projections.UserDetailsProjection;
import com.devsuperior.dsmovie.repositories.UserRepository;
import com.devsuperior.dsmovie.tests.UserDetailsFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import com.devsuperior.dsmovie.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CustomUserUtil userUtil;

	private String existingUserName, nonExistingUserName;
	private UserEntity user;
	private List<UserDetailsProjection> userDetails;

	@BeforeEach
	void setUp() throws Exception {
		existingUserName = "existingUserName@gmail.com";
		nonExistingUserName = "nonExistingUserName@gmail.com";

		user = UserFactory.createUserEntity();
		user.setUsername(existingUserName);
		userDetails = UserDetailsFactory.createCustomClientUser(existingUserName);

		Mockito.when(userRepository.searchUserAndRolesByUsername(existingUserName)).thenReturn(userDetails);
		Mockito.when(userRepository.searchUserAndRolesByUsername(nonExistingUserName)).thenReturn(new ArrayList<>());
		Mockito.when(userRepository.findByUsername(existingUserName)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findByUsername(nonExistingUserName)).thenReturn(Optional.empty());
	}

	@Test
	public void authenticatedShouldReturnUserEntityWhenUserExists() {
		Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingUserName);

		UserEntity result = userService.authenticated();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUserName);
		Mockito.verify(userRepository, Mockito.times(1)).findByUsername(existingUserName);
	}

	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			userService.authenticated();
		});
	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = userService.loadUserByUsername(existingUserName);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUserName);

		Mockito.verify(userRepository, Mockito.times(1)).searchUserAndRolesByUsername(existingUserName);
	}

	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			userService.loadUserByUsername(nonExistingUserName);
		});

		Mockito.verify(userRepository, Mockito.times(1)).searchUserAndRolesByUsername(nonExistingUserName);
	}
}
