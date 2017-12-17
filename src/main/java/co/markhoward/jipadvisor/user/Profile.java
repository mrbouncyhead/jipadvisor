package co.markhoward.jipadvisor.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @NotNull
  @JsonIgnore
  private User user;

  private int profileVersion;

  @Size(min = 3, max = 256, message = "Profile name must be between 3 and 256 characters")
  private String profileName;

  private String imageToken;

  @Size(min = 3, max = 1024, message = "Profile name must be between 3 and 256 characters")
  private String profileText;

  private boolean profileActive;
}
