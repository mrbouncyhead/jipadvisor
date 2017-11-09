package co.markhoward.jipadvisor.database;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseRepo<T> {
  private final EntityManager entityManager;
  private final Class<T> clazz;

  /**
   * Saves an entity to the database
   * @param entity The entity to save
   * @return The entity with the id populated
   */
  public T save(T entity) {
    entityManager.persist(entity);
    return entity;
  }
}
