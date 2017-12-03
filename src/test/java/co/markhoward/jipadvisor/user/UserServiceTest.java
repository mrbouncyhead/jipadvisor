package co.markhoward.jipadvisor.user;

import org.junit.Before;
import org.junit.Test;

import co.markhoward.jipadvisor.security.SecurityController;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
	private UserRepo userRepo;
	private UserService userService;
	private SecurityController securityController;
	
	@Before
	public void setUp () {
		userRepo = mock(UserRepo.class);
		securityController = mock(SecurityController.class);
		userService = new UserService(securityController, userRepo);
	}
	
	@Test
	public void shouldReturnUser () {
		int userId = 1;
		User user = new User ();
		user.setId(userId);
		when(userRepo.get(userId)).thenReturn(user);
		User returned = userService.get(userId);
		assertEquals("Should return the correct user object", returned, user);
	}
}
