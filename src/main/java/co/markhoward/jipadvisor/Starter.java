package co.markhoward.jipadvisor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;

import co.markhoward.jipadvisor.database.JPAHelper;
import co.markhoward.jipadvisor.security.SecurityController;
import co.markhoward.jipadvisor.security.SecurityService;
import co.markhoward.jipadvisor.services.Service;
import co.markhoward.jipadvisor.services.WebServiceUtils;
import co.markhoward.jipadvisor.user.ProfileRepo;
import co.markhoward.jipadvisor.user.ProfileService;
import co.markhoward.jipadvisor.user.User;
import co.markhoward.jipadvisor.user.UserRepo;
import co.markhoward.jipadvisor.user.UserService;
import spark.Spark;

public class Starter {
  public static void main(String[] arguments) {
    CommandLineParser parser = new DefaultParser();
    Options options = new Options();
    options.addOption(Option.builder().required().hasArg().longOpt("key")
        .desc("Adds the location of the keystore").build());

    options.addOption(Option.builder().hasArg().longOpt("frontend")
        .desc("Adds the location of the frontend files, if left blank will use builtin frontend")
        .build());

    try {
      CommandLine commandLine = parser.parse(options, arguments);
      String keyLocation = commandLine.getOptionValue(KEY);
      String frontendLocation = commandLine.getOptionValue(FRONTEND, "");
      Starter start = new Starter();

      if (Strings.isNullOrEmpty(frontendLocation)) {
        Spark.staticFileLocation("jipadvisor-frontend");
      } else {
        Spark.externalStaticFileLocation(frontendLocation);
      }

      start.run(keyLocation);
    } catch (Exception exception) {
      log.error("An error has occurred while starting the application", exception);
      System.exit(1);
    }

  }

  private void run(final String keyLocation) throws Exception {
    // Loads database
    EntityManager entityManager = JPAHelper.getEntityManagerFactory().createEntityManager();
    UserRepo userRepo = new UserRepo(entityManager);
    ProfileRepo profileRepo = new ProfileRepo(entityManager);

    // Loads security classes
    KeyPair keyPair = loadKeyPair(keyLocation);
    Algorithm algorithm =
        Algorithm.RSA512((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
    SecurityController securityController = new SecurityController(algorithm);

    // Loads services and registers routes
    Set<Service> services = Sets.newHashSet();
    services.add(new SecurityService(securityController, userRepo));
    services.add(new UserService(securityController, userRepo));
    services.add(new ProfileService(securityController, profileRepo));
    for (Service service : services)
      service.routes();

    log.info("All services registered");
    addExamples(userRepo, profileRepo);
  }

  private void addExamples(final UserRepo userRepo, final ProfileRepo profileRepo)
      throws IOException {

    List<User> users = WebServiceUtils.jsonToList(
        Resources.toString(Resources.getResource("examples.json"), Charsets.UTF_8), User.class);

    for (User user : users)
      userRepo.save(user);
  }

  private KeyPair loadKeyPair(final String keyLocation) throws Exception {
    File file = new File(keyLocation);
    FileInputStream inputStream = new FileInputStream(file);
    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(inputStream, "password".toCharArray());
    PrivateKey privateKey = (PrivateKey) keystore.getKey("jipadvisor", "password".toCharArray());
    Certificate cert = keystore.getCertificate("jipadvisor");
    PublicKey publicKey = cert.getPublicKey();
    return new KeyPair(publicKey, privateKey);
  }

  private static final Logger log = LoggerFactory.getLogger(Starter.class);
  private static final String KEY = "key";
  private static final String FRONTEND = "frontend";

}
