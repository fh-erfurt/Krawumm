package de.joemiagroup.krawumm.repositories.experimenthasmaterials;

import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
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
public class ExperimentHasMaterialRepositoryImpl implements ExperimentHasMaterialRepositoryCustom {
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
    public List<ExperimentHasMaterial> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<ExperimentHasMaterial> query = builder.createQuery(ExperimentHasMaterial.class);

        final Root<ExperimentHasMaterial> experimenthasmaterial = query.from(ExperimentHasMaterial.class);

        final List<Predicate> predicates = convertToPredicates(builder, experimenthasmaterial, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(experimenthasmaterial.get(sort.getKey())) : builder.desc(experimenthasmaterial.get(sort.getKey())))
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

        final Root<ExperimentHasMaterial> experimenthasmaterial = query.from(ExperimentHasMaterial.class);
        query.select(builder.count(experimenthasmaterial));

        final List<Predicate> predicates = convertToPredicates(builder, experimenthasmaterial, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    /**
     *This method gives all existing ExperimentHasMaterial
     *
     *
     *
     * @return List<ExperimentHasMaterial> holds all existing ExperimentHasMaterial
     */
    @Override
    public List<ExperimentHasMaterial> getAllExperimentHasMaterials() {
        TypedQuery<ExperimentHasMaterial> query =
                em.createQuery("SELECT c FROM ExperimentHasMaterial c", ExperimentHasMaterial.class);
        List<ExperimentHasMaterial> results = query.getResultList();
        return results;
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<ExperimentHasMaterial> experimenthasmaterial, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(experimenthasmaterial.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
