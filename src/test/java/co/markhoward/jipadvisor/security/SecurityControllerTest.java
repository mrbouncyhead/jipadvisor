package co.markhoward.jipadvisor.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityControllerTest {
  private SecurityController securityController;
  
  private static Algorithm ALGORITHM;
  
  @BeforeClass
  public static void setUpClass () throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    ALGORITHM = Algorithm.RSA512((RSAPublicKey)keyPair.getPublic(), (RSAPrivateKey)keyPair.getPrivate());
  }
  
  @Before
  public void setUp () {
    securityController = new SecurityController (ALGORITHM);
  }
  
  @Test
  public void shouldCreateAndVerifyJwtToken () {
    Integer userId = 1000;
    String token = securityController.generateToken (userId);
    assertNotNull("Should generate a token for the user", token);
    Optional<Integer> result = securityController.getUserIdFromToken (token);
    assertEquals("Should return userId", userId, result.get());
  }
}
