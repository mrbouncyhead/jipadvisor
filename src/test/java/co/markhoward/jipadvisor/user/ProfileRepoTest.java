package co.markhoward.jipadvisor.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.markhoward.jipadvisor.database.DatabaseTest;

public class ProfileRepoTest extends DatabaseTest {
  private ProfileRepo profileRepo;
  private User user;

  @Before
  public void setUp() {
    log.info("Setting up user repository");
    profileRepo = new ProfileRepo(entityManager);
  }

  @Test
  public void shouldSaveProfile() {
    Profile profile = new Profile();
    profileRepo.save(profile);
    Assert.assertTrue("Should have populated profile id", profile.getId() > 0);
  }

  private final Logger log = LoggerFactory.getLogger(getClass());
}
