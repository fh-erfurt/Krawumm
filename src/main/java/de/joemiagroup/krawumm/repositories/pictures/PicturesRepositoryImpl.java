package de.joemiagroup.krawumm.repositories.pictures;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Pictures;
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
public class PicturesRepositoryImpl implements PicturesRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Pictures> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Pictures> query = builder.createQuery(Pictures.class);

        final Root<Pictures> pictures = query.from(Pictures.class);

        final List<Predicate> predicates = convertToPredicates(builder, pictures, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(pictures.get(sort.getKey())) : builder.desc(pictures.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    @Override
    public long countByParameters(Map<String, FilterMeta> filters) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<Pictures> pictures = query.from(Pictures.class);
        query.select(builder.count(pictures));

        final List<Predicate> predicates = convertToPredicates(builder, pictures, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Pictures> pictures, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(pictures.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
