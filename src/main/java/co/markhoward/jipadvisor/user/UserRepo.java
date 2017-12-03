package co.markhoward.jipadvisor.user;

import javax.persistence.EntityManager;

import co.markhoward.jipadvisor.database.BaseRepo;

public class UserRepo extends BaseRepo<User> {

  public UserRepo(final EntityManager entityManager) {
    super(entityManager, User.class);
  }
}
