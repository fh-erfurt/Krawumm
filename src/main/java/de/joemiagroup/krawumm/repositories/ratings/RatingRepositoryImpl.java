package de.joemiagroup.krawumm.repositories.ratings;

import de.joemiagroup.krawumm.domains.Rating;
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
public class RatingRepositoryImpl implements RatingRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Rating> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Rating> query = builder.createQuery(Rating.class);

        final Root<Rating> rating = query.from(Rating.class);

        final List<Predicate> predicates = convertToPredicates(builder, rating, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(rating.get(sort.getKey())) : builder.desc(rating.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    @Override
    public long countByParameters(Map<String, FilterMeta> filters) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<Rating> rating = query.from(Rating.class);
        query.select(builder.count(rating));

        final List<Predicate> predicates = convertToPredicates(builder, rating, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Rating> rating, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(rating.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
