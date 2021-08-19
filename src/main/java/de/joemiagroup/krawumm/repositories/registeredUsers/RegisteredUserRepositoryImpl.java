package de.joemiagroup.krawumm.repositories.registeredUsers;

import de.joemiagroup.krawumm.domains.*;
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
public class RegisteredUserRepositoryImpl implements RegisteredUserRepositoryCustom {

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

    /**
     *This method counts the data based on the given filters
     *
     * @param filters
     *
     * @return SingleResult
     */
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

    /**
     *This method finds all User params for a username
     *
     * @param username
     *
     * @return RegisteredUser
     */
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

    /**
     *This method tests if  a Email is already related to a RegisteredUser
     *
     * @param email
     *
     * @return boolean true if email already exists
     */
    public boolean findUserByEmail(String email) {
        TypedQuery<RegisteredUser> query =
                em.createQuery("SELECT c FROM RegisteredUser c", RegisteredUser.class);
        List<RegisteredUser> results = query.getResultList();

        for(RegisteredUser i : results){
            if(i.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     *This method finds all Bookmarks of a RegisteredUser
     *
     * @param user
     *
     * @return List<Bookmark> holds all Bookamrks of a RegisteredUser
     */
    @Override
    public List<Bookmark> findBookmarksOfUser(RegisteredUser user) {
        TypedQuery<Bookmark> query =
                em.createQuery("SELECT c FROM Bookmark c WHERE c.registeredUser.id = ?1", Bookmark.class);
        query.setParameter(1, user.getId());
        List<Bookmark> results = query.getResultList();
        return results;
    }

    /**
     *This method finds all Comments of a RegisteredUser
     *
     * @param user
     *
     * @return List<Comment> holds all Comments of a RegisteredUser
     */
    @Override
    public List<Comment> findCommentsOfUser(RegisteredUser user) {
        TypedQuery<Comment> query =
                em.createQuery("SELECT c FROM Comment c WHERE c.registeredUser.id = ?1", Comment.class);
        query.setParameter(1, user.getId());
        List<Comment> results = query.getResultList();
        return results;
    }

    /**
     *This method finds all Ratings of a RegisteredUser
     *
     * @param user
     *
     * @return List<Rating> holds all Ratings of a given RegisteredUser
     */
    @Override
    public List<Rating> findRatingsOfUser(RegisteredUser user) {
        TypedQuery<Rating> query =
                em.createQuery("SELECT c FROM Rating c WHERE c.registeredUser.id = ?1", Rating.class);
        query.setParameter(1, user.getId());
        List<Rating> results = query.getResultList();
        return results;
    }

    /**
     *This method finds all Experiments of a RegisteredUser
     *
     * @param user
     *
     * @return List<Experiment> holds all Experiments of a given RegisteredUser
     */
    @Override
    public List<Experiment> findExperimentsOfUser(RegisteredUser user) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT c FROM Experiment c WHERE c.registeredUser.id = ?1", Experiment.class);
        query.setParameter(1, user.getId());
        List<Experiment> results = query.getResultList();
        return results;
    }

    /**
     *This method gives all existing RegisteredUsers
     *
     *
     *
     * @return List<RegisteredUser> holds all existing RegisteredUsers
     */
    @Override
    public List<RegisteredUser> getAllRegisteredUsers() {
        TypedQuery<RegisteredUser> query =
                em.createQuery("SELECT c FROM RegisteredUser c", RegisteredUser.class);
        List<RegisteredUser> results = query.getResultList();
        return results;
    }

    /**
     *This method tests if a Usernam is already used
     *
     * @param username
     *
     * @return boolean true if username already used
     */
    public boolean findUserByName(String username) {
         TypedQuery<RegisteredUser> query =
                em.createQuery("SELECT c FROM RegisteredUser c WHERE c.userName = ?1", RegisteredUser.class);
        query.setParameter(1, username);
        List<RegisteredUser> results = query.getResultList();
        for(RegisteredUser i : results){
            if(i.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<RegisteredUser> registeredUser, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(registeredUser.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }
}
