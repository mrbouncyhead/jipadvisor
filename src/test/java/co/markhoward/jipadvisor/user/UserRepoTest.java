package co.markhoward.jipadvisor.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.markhoward.jipadvisor.database.DatabaseTest;

public class UserRepoTest extends DatabaseTest {
  private UserRepo userRepo = new UserRepo(entityManager);


  @Override
  protected void setUpTest() {
    log.info("Setting up user repository");
    userRepo = new UserRepo(entityManager);
  }

  @Test
  public void shouldDealWithNoUserByEmail() {
    Optional<User> result = userRepo.getUniqueWhereProperty(User.EMAIL, EXAMPLE_EMAIL);
    assertFalse("There is no user so this should be empty", result.isPresent());
  }

  @Test
  public void shouldReturnUserByEmail() {
    User user = new User();
    user.setEmail(EXAMPLE_EMAIL);
    user.setPassword("password");
    userRepo.save(user);
    Optional<User> result = userRepo.getUniqueWhereProperty(User.EMAIL, EXAMPLE_EMAIL);
    assertTrue("Should return one result", result.isPresent());
    userRepo.delete(user);
  }

  @Test
  public void shouldSaveUser() {
    User user = new User();
    user.setEmail(EXAMPLE_EMAIL);
    user.setPassword("password");
    userRepo.save(user);
    assertTrue("User should have id populated", user.getId() > 0);
    userRepo.delete(user);
  }

  private static final String EXAMPLE_EMAIL = "joe.bloggs@example.com";
  private final Logger log = LoggerFactory.getLogger(getClass());
}
