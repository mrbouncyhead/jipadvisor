package co.markhoward.jipadvisor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.persistence.EntityManager;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.io.Resources;

import co.markhoward.jipadvisor.database.JPAHelper;
import co.markhoward.jipadvisor.security.SecurityController;
import co.markhoward.jipadvisor.security.SecurityService;
import co.markhoward.jipadvisor.user.UserRepo;
import spark.Spark;

public class Start {
  public static void main(String[] arguments) throws Exception {
    EntityManager entityManager = JPAHelper.getEntityManagerFactory().createEntityManager();
    UserRepo userRepo = new UserRepo(entityManager);
    KeyPair keyPair = loadKeyPair();
    Algorithm algorithm = Algorithm.RSA512((RSAPublicKey)keyPair.getPublic(), (RSAPrivateKey)keyPair.getPrivate());
    SecurityController securityController = new SecurityController(algorithm);
    SecurityService securityService = new SecurityService (securityController, userRepo);
    Spark.get("/hello", (req, res) -> "Hello World");
  }
  
  private static KeyPair loadKeyPair () throws Exception {
    File file = new File(Resources.getResource("keystore.jks").getPath());
    FileInputStream inputStream = new FileInputStream(file);
    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(inputStream, "password".toCharArray());
    PrivateKey privateKey = (PrivateKey) keystore.getKey("jipadvisor", "password".toCharArray());
    Certificate cert = keystore.getCertificate("jipadvisor");
    PublicKey publicKey = cert.getPublicKey();
    return new KeyPair(publicKey, privateKey);
  }
}
