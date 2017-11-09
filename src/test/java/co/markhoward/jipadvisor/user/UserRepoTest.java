package co.markhoward.jipadvisor.user;

import static org.junit.Assert.assertTrue;

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
  
  @Override
  protected void cleanUpTest() {
    
  }

  @Test
  public void shouldSaveUser() {
    User user = new User();
    user.setEmail("joe.bloggs@email.com");
    user.setFullName("Joe Bloggs");
    user.setPassword("password");
    userRepo.save(user);
    assertTrue("User should have id populated", user.getId() > 0);
  }
  
  private final Logger log = LoggerFactory.getLogger(getClass());
}
