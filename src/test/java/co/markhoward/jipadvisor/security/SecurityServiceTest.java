package co.markhoward.jipadvisor.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import co.markhoward.jipadvisor.user.User;
import co.markhoward.jipadvisor.user.UserRepo;

public class SecurityServiceTest {
  private SecurityService securityService;
  private SecurityController securityController;
  private UserRepo userRepo;

  @Before
  public void setUp() {
    securityController = Mockito.mock(SecurityController.class);
    userRepo = Mockito.mock(UserRepo.class);
    securityService = new SecurityService(securityController, userRepo);
  }

  @Test
  public void shouldCallToCreateToken() {
    User user = new User ();
    securityService.register (user);
  }
}
