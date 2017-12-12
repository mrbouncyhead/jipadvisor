package co.markhoward.jipadvisor.user;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import co.markhoward.jipadvisor.database.BaseRepo;

public class UserRepo extends BaseRepo<User> {

  public UserRepo(final EntityManager entityManager) {
    super(entityManager, User.class);
  }

  public Optional<User> findByEmail(final String email) {
    TypedQuery<User> query =
        entityManager.createQuery("Select u From User u WHERE email = :email", User.class);
    query.setParameter("email", email);

    try {
      return Optional.of(query.getSingleResult());
    } catch (NoResultException exception) {
      return Optional.empty();
    }
  }
}
