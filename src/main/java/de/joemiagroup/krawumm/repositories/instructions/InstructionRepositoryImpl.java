package de.joemiagroup.krawumm.repositories.instructions;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Instruction;
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
public class InstructionRepositoryImpl implements InstructionRepositoryCustom {
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
    public List<Instruction> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Instruction> query = builder.createQuery(Instruction.class);

        final Root<Instruction> instruction = query.from(Instruction.class);

        final List<Predicate> predicates = convertToPredicates(builder, instruction, filters);
        query.where(predicates.toArray(new Predicate[0]));

        final List<Order> orderList = sorts.entrySet().stream()
                .map(sort -> sort.getValue().getOrder().isAscending() ? builder.asc(instruction.get(sort.getKey())) : builder.desc(instruction.get(sort.getKey())))
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

        final Root<Instruction> instruction = query.from(Instruction.class);
        query.select(builder.count(instruction));

        final List<Predicate> predicates = convertToPredicates(builder, instruction, filters);
        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> convertToPredicates(final CriteriaBuilder builder, final Root<Instruction> instruction, final Map<String, FilterMeta> filters){
        return filters.values().stream()
                .map(parameter -> builder.equal(instruction.get(parameter.getField()), parameter.getFilterValue()))
                .collect(Collectors.toList());
    }

    public List<String> getInstructionsForExperiment (Experiment experiment) {
        List<String> results = new ArrayList<>();
        System.out.println(experiment.getId());

        TypedQuery<Instruction> query =
                em.createQuery("SELECT i FROM Instruction i WHERE i.experiment.id = ?1", Instruction.class);
        query.setParameter(1, experiment.getId());
        List<Instruction> instructionList = query.getResultList();

        for (Instruction i : instructionList) {
            results.add(i.getText());
        }

        return results;
    }
}
