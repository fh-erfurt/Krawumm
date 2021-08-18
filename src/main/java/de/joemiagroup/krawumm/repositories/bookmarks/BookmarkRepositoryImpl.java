package de.joemiagroup.krawumm.repositories.bookmarks;


import de.joemiagroup.krawumm.domains.Bookmark;
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
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Bookmark> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Bookmark> query = builder.createQuery(Bookmark.class);

        final Root<Bookmark> bookmark = query.from(Bookmark.class);

        final List<Predicate> predicates = convertToPredicates(builder, bookmark, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(bookmark.get(sort.getKey())) : builder.desc(bookmark.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    @Override
    public long countByParameters(Map<String, FilterMeta> filters) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<Bookmark> bookmark = query.from(Bookmark.class);
        query.select(builder.count(bookmark));

        final List<Predicate> predicates = convertToPredicates(builder, bookmark, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Bookmark> bookmark, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(bookmark.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }

    //Own methods
}
