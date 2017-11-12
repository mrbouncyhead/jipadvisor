package co.markhoward.jipadvisor.security;

import co.markhoward.jipadvisor.user.User;
import co.markhoward.jipadvisor.user.UserRepo;
import lombok.RequiredArgsConstructor;
import spark.Spark;

@RequiredArgsConstructor
public class SecurityService {
  private final SecurityController securityController;
  private final UserRepo userRepo;
  
  public void routes () {
    Spark.post("/register", (request, response) -> {
      
      return "";
    });
  }

  public void register(final User user) {
    
  }
}
