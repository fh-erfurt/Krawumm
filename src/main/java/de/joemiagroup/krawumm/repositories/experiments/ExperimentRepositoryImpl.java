package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.Experiment;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ExperimentRepositoryImpl implements ExperimentRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Experiment> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Experiment> query = builder.createQuery(Experiment.class);

        final Root<Experiment> experiment = query.from(Experiment.class);

        final List<Predicate> predicates = convertToPredicates(builder, experiment, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(experiment.get(sort.getKey())) : builder.desc(experiment.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    @Override
    public long countByParameters(Map<String, FilterMeta> filters) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<Experiment> experiment = query.from(Experiment.class);
        query.select(builder.count(experiment));

        final List<Predicate> predicates = convertToPredicates(builder, experiment, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Experiment> experiment, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(experiment.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
