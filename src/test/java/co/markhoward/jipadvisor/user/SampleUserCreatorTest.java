package co.markhoward.jipadvisor.user;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;

import co.markhoward.jipadvisor.security.PasswordHasher;
import co.markhoward.jipadvisor.services.WebServiceUtils;

public class SampleUserCreatorTest {
  @Test
  @Ignore
  public void create4UsersAndSaveAsJson() {
    User user1 = createUser("adam.alister@example.com", "Adam Alister", "man-1.jpeg",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
    User user2 = createUser("albert.alonso@example.com", "Albert Alonso", "man-2.jpeg",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
    User user3 = createUser("agatha.alister@example.com", "Agatha Alister", "woman-1.jpeg",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
    User user4 = createUser("ada.alonso@example.com", "Ada Alonso", "woman-2.jpeg",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

    List<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);
    users.add(user3);
    users.add(user4);

    Optional<String> json = WebServiceUtils.ObjectToJson(users);
    if (!json.isPresent()) fail("Cannot write json");

    System.out.print(json.get());
  }

  private User createUser(final String email, final String profileName, final String imageName,
      final String profileText) {

    User user = new User();
    user.setEmail(email);
    user.setPassword(PasswordHasher.hashPassword("password"));

    Profile profile = new Profile();
    profile.setProfileName(profileName);
    profile.setImageToken(imageName);
    profile.setProfileActive(true);
    profile.setProfileText(profileText);
    profile.setProfileVersion(1);

    user.getProfiles().add(profile);

    return user;
  }
}
