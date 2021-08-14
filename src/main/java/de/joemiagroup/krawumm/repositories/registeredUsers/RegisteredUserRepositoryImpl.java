package de.joemiagroup.krawumm.repositories.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
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
import java.util.stream.Collectors;

@Repository
public class RegisteredUserRepositoryImpl implements RegisteredUserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RegisteredUser> findByParameters(final int page, final int count, final Map<String, FilterMeta> filters, final Map<String, SortMeta> sorts) {

        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<RegisteredUser> query = builder.createQuery(RegisteredUser.class);

        final Root<RegisteredUser> registeredUser = query.from(RegisteredUser.class);

        final List<Predicate> predicates = convertToPredicates(builder, registeredUser, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(registeredUser.get(sort.getKey())) : builder.desc(registeredUser.get(sort.getKey())))
                .collect(Collectors.toList());
        query.orderBy(orderList);

        return this.em.createQuery(query).setFirstResult(page * count).setMaxResults(count).getResultList();
    }

    @Override
    public long countByParameters(final Map<String, FilterMeta> filters) {

        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);

        final Root<RegisteredUser> registeredUser = query.from(RegisteredUser.class);
        query.select(builder.count(registeredUser));

        final List<Predicate> predicates = convertToPredicates(builder, registeredUser, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }


     public boolean findUserByName(String username) {
         TypedQuery<RegisteredUser> query =
                em.createQuery("SELECT c FROM RegisteredUser c", RegisteredUser.class);
        List<RegisteredUser> results = query.getResultList();

        for(RegisteredUser i : results){
            if(i.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public RegisteredUser findUserDataByName(String username) {
        TypedQuery<RegisteredUser> query =
                em.createQuery("SELECT c FROM RegisteredUser c", RegisteredUser.class);
        List<RegisteredUser> results = query.getResultList();

        for(int i = 0; i < results.size(); i++){
            if(results.get(i).getUserName().equals(username)){
                return results.get(i);
            }
        }
        return null;
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<RegisteredUser> registeredUser, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(registeredUser.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
