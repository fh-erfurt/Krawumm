package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.web.experiments.FilterView;
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
public class ExperimentRepositoryImpl implements ExperimentRepositoryCustom {
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
    /**
     *This method gets all experiments that are released
     *
     * @param released boolean whether an experiment is released or not
     *
     * @return ResultList
     */
    public List<Experiment> getAllExperiments(TrueFalse released) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT c FROM Experiment c WHERE c.isReleased = ?1", Experiment.class);
        query.setParameter(1, released);
        List<Experiment> results = query.getResultList();

        return results;
    }

    /**
     *This method gets all comments of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    public List<Comment> getCommentsForExperiment(Experiment data) {
        TypedQuery<Comment> query =
                em.createQuery("SELECT c FROM Comment c WHERE c.experiment.id = ?1", Comment.class);
        query.setParameter(1, data.getId());
        List<Comment> results = query.getResultList();

        return results;
    }

    /**
     *This method checks whether an experiment is released or not
     *
     * @param id primary key of an experiment
     *
     * @return boolean
     */
    @Override
    public boolean isExperimentReleased(long id) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT c FROM Experiment c WHERE c.id = ?1", Experiment.class);
        query.setParameter(1, id);
        List<Experiment> results = query.getResultList();
        if (results.get(0).getIsReleased().equals(TrueFalse.T)){
            return true;
        } else return false;
    }

    /**
     *This method gets ratings of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    @Override
    public List<Rating> getRatingsForExperiment(Experiment data) {
        TypedQuery<Rating> query =
                em.createQuery("SELECT c FROM Rating c WHERE c.experiment.id = ?1", Rating.class);
        query.setParameter(1, data.getId());
        List<Rating> results = query.getResultList();

        return results;
    }

    /**
     *This method gets instructions of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    @Override
    public List<Instruction> getInstructionsForExperiment(Experiment data) {
        TypedQuery<Instruction> query =
                em.createQuery("SELECT c FROM Instruction c WHERE c.experiment.id = ?1", Instruction.class);
        query.setParameter(1, data.getId());
        List<Instruction> results = query.getResultList();

        return results;
    }

    /**
     *This method gets pictures of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    @Override
    public List<Pictures> getPicturesForExperiment(Experiment data) {
        TypedQuery<Pictures> query =
                em.createQuery("SELECT c FROM Pictures c WHERE c.experiment.id = ?1", Pictures.class);
        query.setParameter(1, data.getId());
        List<Pictures> results = query.getResultList();

        return results;
    }

    /**
     *This method gets bookmarks of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    @Override
    public List<Bookmark> getBookmarksForExperiment(Experiment data) {
        TypedQuery<Bookmark> query =
                em.createQuery("SELECT c FROM Bookmark c WHERE c.experiment.id = ?1", Bookmark.class);
        query.setParameter(1, data.getId());
        List<Bookmark> results = query.getResultList();

        return results;
    }

    /**
     *This method gets materials of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    @Override
    public List<ExperimentHasMaterial> getExperimentHasMaterialsForExperiment(Experiment data) {
        TypedQuery<ExperimentHasMaterial> query =
                em.createQuery("SELECT c FROM ExperimentHasMaterial c WHERE c.experiment.id = ?1", ExperimentHasMaterial.class);
        query.setParameter(1, data.getId());
        List<ExperimentHasMaterial> results = query.getResultList();

        return results;
    }

    /**
     *This method searches for experiments based on a string
     *
     * @param search string that should be in the name of an experiment
     *
     * @return ResultList
     */
    public List<Experiment> lookForStringInExperimentName(String search) {
        List<Experiment> searchResult = new ArrayList<>();

        List<Experiment> experimentList = this.getAllExperiments(TrueFalse.T);
        String[] keywords = search.split(" ");

        for (String keyword : keywords) {
            for (Experiment experiment : experimentList) {
                if (experiment.getExperimentName().contains(keyword)){
                    boolean isInArray = false;
                    for(int i  = 0; i <searchResult.size(); i++){
                        if(searchResult.get(i) == experiment){
                            isInArray = true;
                            break;
                        }
                    }
                    if(!isInArray){
                        searchResult.add(experiment);
                    }
                }
            }
        }
        if(searchResult.size()==0){
            System.out.println("Keine Suchergebnisse vorhanden!");
        }
        return searchResult;
    }

    /**
     *This method gets materials of an experiment
     *
     * @param data specific experiment
     *
     * @return ResultList
     */
    public List<String> getMaterialsForExperiment(Experiment data) {
        List<String> results = new ArrayList<>();

        TypedQuery<ExperimentHasMaterial> query =
                em.createQuery("SELECT m FROM ExperimentHasMaterial m WHERE m.experiment.id = ?1", ExperimentHasMaterial.class);
        query.setParameter(1, data.getId());
        List<ExperimentHasMaterial> materialList = query.getResultList();

        for (ExperimentHasMaterial m : materialList) {
            results.add(m.getMaterial().getMaterialName());
        }

        return results;
    }

    /**
     *This method gets experiment by id
     *
     * @param id primary key of an experiment
     *
     * @return SingleResult
     */
    public Experiment getExperimentById (long id) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT e FROM Experiment e WHERE e.id = ?1", Experiment.class);
        query.setParameter(1, id);
        List<Experiment> results = query.getResultList();

        return results.get(0);
    }

    /**
     *This method gets the experiment that was inserted last
     *
     * @return SingleResult
     */
    @Override
    public Experiment getLastInsertedExperiment() {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT e FROM Experiment e", Experiment.class);
        List<Experiment> results = query.getResultList();

        int lastPosition = results.size() -1;
        return results.get(lastPosition);
    }

    /**
     *This method gets all experiments that match the filters
     *
     * @param filters
     *
     * @return ResultList
     */
    @Override
    public List<Experiment> useFilterOnExperiment(FilterView filters){

        // list which gets filled with the result Experiments
        List<Experiment> filterList;
        // lists that will get filled with the results of each filter individually
        List<Experiment> ageList = new ArrayList<Experiment>();
        List<Experiment> difficultyList = new ArrayList<Experiment>();
        List<Experiment> finalRatingList = new ArrayList<Experiment>();
        List<Experiment> durationList = new ArrayList<Experiment>();
        List<Experiment> indoorOutdoorList = new ArrayList<Experiment>();
        // get list of all Experiments
        List<Experiment> experimentList = this.getAllExperiments(TrueFalse.T);

        // puts all Experiments, which match the filter if the filter is set, in their specific list
        if (!Objects.isNull(filters.getMinAge())) {
            for (Experiment experiment : experimentList) {
                if (filters.getMinAge() >= experiment.getAge()) {
                    ageList.add(experiment);
                }
            }
        }

        if (!Objects.isNull(filters.getMaxDifficulty())) {
            for (Experiment experiment : experimentList) {
                if (filters.getMaxDifficulty() >= experiment.getDifficulty()) {
                    difficultyList.add(experiment);
                }
            }
        }

        if (!Objects.isNull(filters.getMinRating())) {
            for (Experiment experiment : experimentList) {
                TypedQuery<Rating> query =
                        em.createQuery("SELECT r FROM Rating r WHERE r.experiment.id = ?1", Rating.class);
                query.setParameter(1, experiment.getId());
                List<Rating> ratingList = query.getResultList();

                float finalRating = 0.0f;

                for (Rating rating : ratingList) {
                    finalRating = finalRating + rating.getRatingValue();
                }

                finalRating = finalRating/ratingList.size();

                if (filters.getMinRating() <= finalRating) {
                    finalRatingList.add(experiment);
                }
            }
        }

        if (!Objects.isNull(filters.getMaxTime())) {
            for (Experiment experiment : experimentList) {
                if (filters.getMaxTime() >= experiment.getDuration()) {
                    durationList.add(experiment);
                }
            }
        }

        if (!Objects.isNull(filters.getLocation())) {
            IndoorOutdoor loc;
            if (filters.getLocation().equals("indoor")) {
                loc = IndoorOutdoor.I;
            } else {
                loc = IndoorOutdoor.O;
            }
            for (Experiment experiment : experimentList) {
                if (loc == experiment.getIndoorOutdoor()) {
                    indoorOutdoorList.add(experiment);
                }
            }
        }


        // compares all existing filter lists and puts all Experiments, which match all set filters, in the return/filterList
        // if age filter was set check if other filters were set
        if (!ageList.isEmpty()){
            filterList = ageList;
            if (!difficultyList.isEmpty()){
                filterList = compare(filterList, difficultyList);
            }
            if (!finalRatingList.isEmpty()){
                filterList = compare(filterList, finalRatingList);
            }
            if (!durationList.isEmpty()){
                filterList = compare(filterList, durationList);
            }
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age filter not set check if difficulty filter was set and check if other filters were set
        else if (!difficultyList.isEmpty()){
            filterList = difficultyList;
            if (!finalRatingList.isEmpty()){
                filterList = compare(filterList, finalRatingList);
            }
            if (!durationList.isEmpty()){
                filterList = compare(filterList, durationList);
            }
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age and difficulty filter not set check if finalRating filter was set and check if other filters were set
        else if (!finalRatingList.isEmpty()){
            filterList = finalRatingList;
            if (!durationList.isEmpty()){
                filterList = compare(filterList, durationList);
            }
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age, difficulty and finalRating filter not set check if duration filter was set and check if other filters were set
        else if (!durationList.isEmpty()){
            filterList = durationList;
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age, difficulty, finalRating and duration filter not set check if indoorOutdoor filter was set
        else if (!indoorOutdoorList.isEmpty()){
            filterList = indoorOutdoorList;
        }
        // no filters set show all Experiments
        else filterList = this.getAllExperiments(TrueFalse.T);
        return filterList;
    }

    private List<Experiment> compare(List<Experiment> resultList, List<Experiment> compareList ){
        List<Experiment> removeList = new ArrayList<>();
        for (Experiment experimentResult : resultList) {
            boolean existsInList = false;
            for (Experiment experimentCompare : compareList) {
                if (experimentResult == experimentCompare) {
                    existsInList = true;
                    break;
                }
            }
            if (!existsInList){
                //resultList.remove(experimentResult);
                removeList.add(experimentResult);
            }
        }
        for (Experiment removeExperiment : removeList) {
            resultList.remove(removeExperiment);
        }
        return resultList;
    }

    /**
     *This method gets the rating for an experiment by a user
     *
     * @param user specific user
     * @param experiment specific experiment
     *
     * @return SingleResult
     */
    @Override
    public int getRatingOfExperimentForUser(RegisteredUser user, Experiment experiment) {
        TypedQuery<Rating> query =
                em.createQuery("SELECT e FROM Rating e WHERE e.registeredUser.id = ?1 and e.experiment.id = ?2", Rating.class);
        query.setParameter(1, user.getId());
        query.setParameter(2, experiment.getId());
        List<Rating> results = query.getResultList();
        if (results.isEmpty()){
            return 0;
        }
        return results.get(0).getRatingValue();
    }

    /**
     *This method gets the bookmark for an experiment by a user
     *
     * @param user specific user
     * @param experiment specific experiment
     *
     * @return SingleResult
     */
    @Override
    public boolean getBookmarkOfExperiment(RegisteredUser user, Experiment experiment) {
        TypedQuery<Bookmark> query =
                em.createQuery("SELECT e FROM Bookmark e WHERE e.registeredUser.id = ?1 and e.experiment.id = ?2", Bookmark.class);
        query.setParameter(1, user.getId());
        query.setParameter(2, experiment.getId());
        List<Bookmark> results = query.getResultList();
        if (results.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     *This method gets the bookmark for an experiment by a user
     *
     * @param user specific user
     * @param experiment specific experiment
     *
     * @return SingleResult
     */
    @Override
    public Bookmark getBookmarkDataOfExperiment(RegisteredUser user, Experiment experiment) {
        TypedQuery<Bookmark> query =
                em.createQuery("SELECT e FROM Bookmark e WHERE e.registeredUser.id = ?1 and e.experiment.id = ?2", Bookmark.class);
        query.setParameter(1, user.getId());
        query.setParameter(2, experiment.getId());
        List<Bookmark> results = query.getResultList();
        if (!results.isEmpty()){
            return results.get(0);
        }
        return null;
    }

    /**
     *This method gets all experiments by a user
     *
     * @param user specific user
     *
     * @return Resultlist
     */
    public List<Experiment> getExperimentsForUser(RegisteredUser user) {
        TypedQuery<Experiment> query =
                em.createQuery("SELECT e FROM Experiment e WHERE e.registeredUser.id = ?1 AND e.isReleased = ?2", Experiment.class);
        query.setParameter(1, user.getId());
        query.setParameter(2, TrueFalse.T);
        return query.getResultList();
    }
}
