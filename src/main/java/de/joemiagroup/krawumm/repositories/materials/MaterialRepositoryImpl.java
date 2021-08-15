package de.joemiagroup.krawumm.repositories.materials;

import de.joemiagroup.krawumm.domains.Material;
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
public class MaterialRepositoryImpl implements MaterialRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

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

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Material> material, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(material.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
