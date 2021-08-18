package de.joemiagroup.krawumm.repositories.comments;


import de.joemiagroup.krawumm.domains.Comment;
import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Pictures;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.web.experiments.ExperimentView;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Comment> query = builder.createQuery(Comment.class);

        final Root<Comment> comment = query.from(Comment.class);

        final List<Predicate> predicates = convertToPredicates(builder, comment, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(comment.get(sort.getKey())) : builder.desc(comment.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    @Override
    public long countByParameters(Map<String, FilterMeta> filters) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<Comment> comment = query.from(Comment.class);
        query.select(builder.count(comment));

        final List<Predicate> predicates = convertToPredicates(builder, comment, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Comment> comment, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(comment.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }

    //Own methods
}
