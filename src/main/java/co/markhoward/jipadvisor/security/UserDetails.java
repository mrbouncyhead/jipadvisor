package co.markhoward.jipadvisor.security;

import lombok.Data;

@Data
public class UserDetails {
  private int id;
  private String email;
  private String token;
}
