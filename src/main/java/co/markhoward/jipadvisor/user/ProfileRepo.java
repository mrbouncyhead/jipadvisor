package co.markhoward.jipadvisor.user;

import java.util.List;

import javax.persistence.EntityManager;

import co.markhoward.jipadvisor.database.BaseRepo;

public class ProfileRepo extends BaseRepo<Profile> {

  public ProfileRepo(final EntityManager entityManager) {
    super(entityManager, Profile.class);
  }

  public List<Profile> listProfiles(final int userId) {
    return entityManager.createQuery("From Profile", Profile.class).getResultList();
  }

}
