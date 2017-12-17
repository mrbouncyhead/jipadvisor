package co.markhoward.jipadvisor.security;

import java.util.Optional;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

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
      try {
        userRepo.save(user);
      } catch (ConstraintViolationException exception) {
        response.status(409);
        return WebServiceResponses.ERROR_409;
      }

      Optional<String> responseJson =
          WebServiceUtils.ObjectToJson(createUserDetails(user.getId(), user.getEmail()));
      return responseJson.get();
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
      if (!savedUserResult.isPresent()
          || !savedUserResult.get().getPassword().equals(user.getPassword())) {
        response.status(401);
        return WebServiceResponses.ERROR_401;
      }

      User savedUser = savedUserResult.get();
      Optional<String> responseJson =
          WebServiceUtils.ObjectToJson(createUserDetails(savedUser.getId(), savedUser.getEmail()));
      return responseJson.get();
    });

    Spark.exception(RollbackException.class, (exception, request, response) -> {
      response.status(409);
      response.body("User is invalid or already exists");
    });
  }

  private UserDetails createUserDetails(final int id, final String email) {
    UserDetails userDetails = new UserDetails();
    userDetails.setId(id);
    userDetails.setToken(securityController.generateToken(id));
    userDetails.setEmail(email);
    return userDetails;
  }

  private final Logger log = LoggerFactory.getLogger(getClass());
}
