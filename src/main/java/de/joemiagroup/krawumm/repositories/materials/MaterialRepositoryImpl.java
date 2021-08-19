package de.joemiagroup.krawumm.repositories.materials;

import de.joemiagroup.krawumm.domains.Material;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MaterialRepositoryImpl implements MaterialRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    /**
     *This method searches for data based on the given parameter
     *
     * @param page
     * @param count
     * @param filters
     * @param sorts
     *
     * @return ResultList
     */
    @Override
    public List<Material> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Material> query = builder.createQuery(Material.class);

        final Root<Material> material = query.from(Material.class);

        final List<Predicate> predicates = convertToPredicates(builder, material, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(material.get(sort.getKey())) : builder.desc(material.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    /**
     *This method counts the data based on the given filters
     *
     * @param filters
     *
     * @return SingleResult
     */
    @Override
    public long countByParameters(Map<String, FilterMeta> filters) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<Material> material = query.from(Material.class);
        query.select(builder.count(material));

        final List<Predicate> predicates = convertToPredicates(builder, material, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    /**
     *This method finds a Material by its Name
     *
     *
     *
     * @return List<Material> holds found Material
     */
    @Override
    public List<Material> findMaterialByName(String name) {
        TypedQuery<Material> query =
                em.createQuery("SELECT e FROM Material e WHERE e.materialName = ?1 ", Material.class);
        query.setParameter(1, name);
        List<Material> results = query.getResultList();
        return results;
    }

    /**
     *This method gives back the last inserted Material
     *
     *
     *
     * @return Material is the last inserted Material
     */
    @Override
    public Material getLastInsertedMaterial() {
        TypedQuery<Material> query =
                em.createQuery("SELECT e FROM Material e", Material.class);
        List<Material> results = query.getResultList();

        int lastPosition = results.size() - 1;
        return results.get(lastPosition);
    }

    /**
     *This method gives all existing Materials
     *
     *
     *
     * @return List<Material> holds all existing Materials
     */
    @Override
    public List<Material> getAllMaterials() {
        TypedQuery<Material> query =
                em.createQuery("SELECT c FROM Material c", Material.class);
        List<Material> results = query.getResultList();
        return results;
    }

    /**
     *This method finds a Material by its Id
     *
     *
     *
     * @return Material is found Material else null
     */
    @Override
    public Material getMaterialById(long id) {
        TypedQuery<Material> query =
                em.createQuery("SELECT c FROM Material c where c.id = ?1", Material.class);
        query.setParameter(1, id);
        List<Material> results = query.getResultList();
        if (results.size() > 0) return results.get(0);
        else return null;
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Material> material, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(material.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }


}
