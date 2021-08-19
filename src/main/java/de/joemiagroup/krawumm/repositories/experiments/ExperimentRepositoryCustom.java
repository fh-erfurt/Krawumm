package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.web.experiments.FilterView;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for the ExperimentRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

public interface ExperimentRepositoryCustom {
    List<Experiment> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

    List<Experiment> getAllExperiments(TrueFalse t);
    List<Comment> getCommentsForExperiment(Experiment data);
    boolean isExperimentReleased(long id);
    List<Rating> getRatingsForExperiment(Experiment data);
    List<Instruction> getInstructionsForExperiment(Experiment data);
    List<Pictures> getPicturesForExperiment(Experiment data);
    List<Bookmark> getBookmarksForExperiment(Experiment data);
    List<ExperimentHasMaterial> getExperimentHasMaterialsForExperiment(Experiment data);
    List<Experiment> lookForStringInExperimentName(String search);
    List<String> getMaterialsForExperiment(Experiment experiment);
    Experiment getExperimentById (long id);
    Experiment getLastInsertedExperiment();
    int getRatingOfExperimentForUser(RegisteredUser user, Experiment experiment);
    boolean getBookmarkOfExperiment(RegisteredUser user, Experiment experiment);
    Bookmark getBookmarkDataOfExperiment(RegisteredUser user, Experiment experiment);
    List<Experiment> useFilterOnExperiment(FilterView filters);
    List<Experiment> getExperimentsForUser(RegisteredUser user);
}
