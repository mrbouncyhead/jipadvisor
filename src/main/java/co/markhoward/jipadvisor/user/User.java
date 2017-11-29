package co.markhoward.jipadvisor.user;

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
  
  @NotNull
  @Size(min = 3, max = 256, message = "Name must be between 3 and 256 characters")
  private String fullName;
  
  @Email
  @Size(min = 3, max = 256, message = "Email must be between 3 and 256 characters")
  private String email;
  
  @NotNull
  private String password;
  
  public static String USER_ID = "userId";
  
}
