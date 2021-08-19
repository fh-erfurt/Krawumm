package de.joemiagroup.krawumm.repositories;
import de.joemiagroup.krawumm.domains.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class is the base for the repositories
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

@RequiredArgsConstructor
public abstract class BaseRepository<E extends BaseEntity> {

    protected final EntityManager entityManager;
    private final Class<E> type;

    /**
     *This method saves an entity to the database
     *
     * @param entity data row for a specific table of the database
     *
     * @return id of the saved data
     */
    public Long save(E entity) {

        entityManager.getTransaction().begin();
        if(Objects.isNull(entity.getId())){
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
        entityManager.getTransaction().commit();

        return entity.getId();
    }

    /**
     *This method searches for data based on the id
     *
     * @param id primary key of each table of the database
     *
     * @return data that was looked for
     */
    public Optional<E> findBy(Long id) {
        return Optional.ofNullable(entityManager.find(type, id));
    }

    /**
     *This method searches for all data of a specific table of the database
     *
     * @return ResultList all rows of data of the specific table of the database
     */
    public List<E> findAll() {
        return entityManager.createQuery("SELECT entity FROM " + type.getCanonicalName(), type ).getResultList();
    }

    /**
     *This method deletes a specific row of a table of the database
     *
     * @param entity data row for a specific table of the database
     *
     * no return value
     */
    public void delete(E entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    /**
     *This method deletes all rows of data of a table of the database
     *
     * no return value
     */
    public void deleteAll() {
        entityManager.getTransaction().begin();

        entityManager.createQuery("DELETE FROM " + type.getCanonicalName()).executeUpdate();

        entityManager.getTransaction().commit();
    }
}
