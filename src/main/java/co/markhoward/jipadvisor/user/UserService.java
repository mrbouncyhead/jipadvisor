package co.markhoward.jipadvisor.user;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.markhoward.jipadvisor.security.SecurityController;
import co.markhoward.jipadvisor.services.Service;
import co.markhoward.jipadvisor.services.WebServiceUtils;
import lombok.RequiredArgsConstructor;
import spark.Spark;

@RequiredArgsConstructor
public class UserService implements Service {
	private final UserRepo userRepo;
	private final SecurityController securityController;

	@Override
	public void routes() {
		log.info("Registering GET /user");
		Spark.get("/user", (request, response) -> {
			String token = request.headers("token");
			Optional<Integer> userIdResult = securityController.getUserIdFromToken(token);
			if(!userIdResult.isPresent()) {
				response.status(401);
				return "User cannot be found";
			}
			
			User user = userRepo.get(userIdResult.get());
			Optional<String> result = WebServiceUtils.ObjectToJson(user);
			if(result.isPresent())
				return result.get();
			
			response.status(500);
			return "There was an error getting the user";
		});
	}

	public User get(final int userId) {
		return userRepo.get(userId);
	}

	private final Logger log = LoggerFactory.getLogger(getClass());
}
