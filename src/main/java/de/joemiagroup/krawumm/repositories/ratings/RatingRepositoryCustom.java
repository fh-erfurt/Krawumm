package de.joemiagroup.krawumm.repositories.ratings;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Rating;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for the RatingRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

public interface RatingRepositoryCustom {
    List<Rating> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

    float getRatingForExperiment(Experiment data);
}