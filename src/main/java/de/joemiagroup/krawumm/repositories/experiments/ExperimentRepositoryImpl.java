package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.web.experiments.ExperimentDataView;
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

    //Own methods
    public List<Experiment> getAllExperiments(TrueFalse released) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT c FROM Experiment c WHERE c.isReleased = ?1", Experiment.class);
        query.setParameter(1, released);
        List<Experiment> results = query.getResultList();

        return results;
    }

    public List<Comment> getCommentsForExperiment(Experiment data) {
        TypedQuery<Comment> query =
                em.createQuery("SELECT c FROM Comment c WHERE c.experiment.id = ?1", Comment.class);
        query.setParameter(1, data.getId());
        List<Comment> results = query.getResultList();

        return results;
    }

    public List<Experiment> lookForStringInExperimentName(String search) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT c FROM Experiment c WHERE c.experimentName LIKE CONCAT('%',?1,'%')", Experiment.class);
        query.setParameter(1, search);
        List<Experiment> results = query.getResultList();

        return results;
    }

    public List<String> getMaterialsForExperiment(Experiment experiment) {
        List<String> results = new ArrayList<>();

        TypedQuery<ExperimentHasMaterial> query =
                em.createQuery("SELECT m FROM ExperimentHasMaterial m WHERE m.experiment.id = ?1", ExperimentHasMaterial.class);
        query.setParameter(1, experiment.getId());
        List<ExperimentHasMaterial> materialList = query.getResultList();

        for (ExperimentHasMaterial m : materialList) {
            results.add(m.getMaterial().getMaterialName());
        }

        return results;
    }

    public void writeCommentDataInDatabase() {}
}
