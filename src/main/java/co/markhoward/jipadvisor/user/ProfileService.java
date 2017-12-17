package co.markhoward.jipadvisor.user;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.markhoward.jipadvisor.security.SecurityController;
import co.markhoward.jipadvisor.services.Service;
import co.markhoward.jipadvisor.services.WebServiceUtils;
import lombok.RequiredArgsConstructor;
import spark.Spark;

@RequiredArgsConstructor
public class ProfileService implements Service {
  private final SecurityController securityController;
  private final ProfileRepo profileRepo;

  @Override
  public void routes() {
    log.info("Registering GET /profile");
    Spark.get("/profile", (request, response) -> {
      String token = request.headers("token");
      Optional<Integer> userIdResult = securityController.getUserIdFromToken(token);
      if (!userIdResult.isPresent()) {
        response.status(401);
        return "User cannot be found";
      }

      List<Profile> profiles = profileRepo.listProfiles(userIdResult.get());
      Optional<String> responseJson = WebServiceUtils.ObjectToJson(profiles);
      return responseJson.get();
    });
  }

  private final Logger log = LoggerFactory.getLogger(getClass());
}
