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
      Optional<User> user = WebServiceUtils.jsonToObject(request.body(), User.class);
      if (!user.isPresent()) {
        response.status(401);
        return WebServiceResponses.ERROR_401;
      }

      return register(user.get());
    });
    log.info("Registering POST /login");
    Spark.post("/login", (request, response) -> {
      return "";
    });
  }

  public String register(final User user) {
    userRepo.save(user);    
    return securityController.generateToken(user.getId());
  }

  private final Logger log = LoggerFactory.getLogger(getClass());
}
