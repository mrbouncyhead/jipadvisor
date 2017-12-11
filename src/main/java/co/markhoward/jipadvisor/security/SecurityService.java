package co.markhoward.jipadvisor.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.markhoward.jipadvisor.services.Service;
import co.markhoward.jipadvisor.services.WebServiceResponses;
import co.markhoward.jipadvisor.services.WebServiceUtils;
import co.markhoward.jipadvisor.user.User;
import co.markhoward.jipadvisor.user.UserRepo;
import lombok.RequiredArgsConstructor;
import spark.Spark;

@RequiredArgsConstructor
public class SecurityService implements Service {
  private final SecurityController securityController;
  private final UserRepo userRepo;

  public void routes() {
    log.info("Registering POST /register");
    Spark.post("/register", (request, response) -> {
      Optional<User> result = WebServiceUtils.jsonToObject(request.body(), User.class);
      if (!result.isPresent()) {
        response.status(401);
        return WebServiceResponses.ERROR_401;
      }

      User user = result.get();
      user.setPassword(PasswordHasher.hashPassword(user.getPassword()));
      Optional<User> savedUserResult = userRepo.getUniqueWhereProperty(User.EMAIL, user.getEmail());
      if (savedUserResult.isPresent()) {
        response.status(409);
        return WebServiceResponses.ERROR_409;
      }

      userRepo.save(user);
      return securityController.generateToken(user.getId());
    });

    log.info("Registering GET /login");
    Spark.post("/login", (request, response) -> {
      Optional<User> result = WebServiceUtils.jsonToObject(request.body(), User.class);
      if (!result.isPresent()) {
        response.status(401);
        return WebServiceResponses.ERROR_401;
      }

      User user = result.get();
      user.setPassword(PasswordHasher.hashPassword(user.getPassword()));
      Optional<User> savedUserResult = userRepo.getUniqueWhereProperty(User.EMAIL, user.getEmail());
      if (!savedUserResult.isPresent()) {
        response.status(401);
        return WebServiceResponses.ERROR_401;
      }

      return securityController.generateToken(user.getId());
    });
  }

  private final Logger log = LoggerFactory.getLogger(getClass());
}
