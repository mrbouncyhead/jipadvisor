package co.markhoward.jipadvisor.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Email
  @Size(min = 3, max = 256, message = "Email must be between 3 and 256 characters")
  @Column(unique = true)
  private String email;

  @NotNull
  private String password;

  public static String USER_ID = "userId";
  public static final String EMAIL = "email";
}
