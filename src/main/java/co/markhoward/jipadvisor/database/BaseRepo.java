package co.markhoward.jipadvisor.database;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseRepo<T> {
  protected final EntityManager entityManager;
  protected final Class<T> clazz;

  /**
   * Saves an entity to the database
   * 
   * @param entity The entity to save
   * @return The entity with the id populated
   */
  public T save(T entity) {
    entityManager.getTransaction().begin();
    entityManager.persist(entity);
    entityManager.getTransaction().commit();
    log.info("Saved entity with: {}", entity);
    return entity;
  }

  /**
   * Deletes an entity from the database
   * 
   * @param entity The entity to delete
   */
  public void delete(final T entity) {
    entityManager.getTransaction().begin();
    entityManager.remove(entity);
    entityManager.getTransaction().commit();
  }

  /**
   * Returns an entity based on the entity id
   * 
   * @param id The id of the entity
   * @return The entity if it is found
   */
  public T get(int id) {
    T entity = entityManager.find(clazz, id);
    return entity;
  }

  /**
   * Returns a single entity if it exists
   * 
   * @param property The property to test
   * @param value The value
   * @return An optional, filled with the entity if it exists
   */
  public Optional<T> getUniqueWhereProperty(final String property, final String value) {
    List<T> entities = getWhereProperty(property, value);
    if (entities.isEmpty()) return Optional.empty();

    return Optional.of(entities.get(0));
  }

  /**
   * Gets all entities that have a specific value
   * 
   * @param property The property to test
   * @param value The value
   * @return Entities with that value
   */
  public List<T> getWhereProperty(final String property, final String value) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(clazz);
    Root<T> root = criteria.from(clazz);
    criteria.select(root);
    criteria.where(builder.equal(root.get(property), value));
    return entityManager.createQuery(criteria).getResultList();
  }

  private final Logger log = LoggerFactory.getLogger(this.getClass());
}
