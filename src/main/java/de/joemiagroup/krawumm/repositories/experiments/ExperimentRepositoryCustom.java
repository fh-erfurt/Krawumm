package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.domains.Comment;
import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.TrueFalse;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

public interface ExperimentRepositoryCustom {
    List<Experiment> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

    List<Experiment> getAllExperiments(TrueFalse t);
    List<Comment> getCommentsForExperiment(Experiment data);
    List<Experiment> lookForStringInExperimentName(String search);
}
