package co.markhoward.jipadvisor.security;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class PasswordHasher {
  private static HashFunction hashFunction;

  /**
   * Given a string, this method will return a hash
   * 
   * @param password The password to hash
   * @return The hash to return
   */
  public static String hashPassword(final String password) {
    if (Strings.isNullOrEmpty(password)) throw new RuntimeException("Password is null or blank");
    if (null == hashFunction) hashFunction = Hashing.sha512();

    HashCode hashCode = hashFunction.newHasher().putString(password, Charsets.UTF_8).hash();
    return hashCode.toString();
  }
}
