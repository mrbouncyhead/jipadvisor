package co.markhoward.jipadvisor.security;

import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import co.markhoward.jipadvisor.user.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityController {
  private final Algorithm algorithm;

  public String generateToken(final int userId) {
    Date now = new Date ();
    return JWT.create()
        .withIssuer(ISSUER)
        .withClaim(User.USER_ID, userId)
        .withIssuedAt(now)
        .sign(algorithm);
  }
  
  public Optional<Integer> getUserIdFromToken(String token) {
    DecodedJWT decoded = JWT.require(algorithm).build().verify(token);
    Claim claim = decoded.getClaim(User.USER_ID);
    return Optional.of(claim.asInt());
  }
  
  private static String ISSUER = "jipadvisor";
}
