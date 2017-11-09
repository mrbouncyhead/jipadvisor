package co.markhoward.jipadvisor.database;

import static org.junit.Assert.assertFalse;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class DatabaseTest {
  protected static EntityManager entityManager;

  @BeforeClass
  public static void setUpTests() {
    entityManager = JPATestHelper.getEntityManagerFactory().createEntityManager();
    log.info("Got test persistence unit");
  }
  
  @Before
  public void setUp () {
    entityManager.getTransaction().begin();
    log.info("Started transaction");
    setUpTest();
  }
  
  @After
  public void cleanUp () {
    entityManager.getTransaction().commit();
    log.info("Ended transaction");
    cleanUpTest();
  }

  @Test
  public void shouldNotReturnNullForDatabaseVersion() {
    final String sql = "SELECT H2VERSION() FROM DUAL";
    String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
    assertFalse("Should return version", Strings.isNullOrEmpty(result));
  }
  
  protected void setUpTest () {
    // Should be overridden
  }
  
  protected void cleanUpTest () {
    // Should be overridden
  }
  
  private static final Logger log = LoggerFactory.getLogger(DatabaseTest.class);
}
