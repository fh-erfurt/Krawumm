package de.joemiagroup.krawumm.repositories.pictures;

import de.joemiagroup.krawumm.domains.Comment;
import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Pictures;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for the PicturesRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

public interface PicturesRepositoryCustom {
    List<Pictures> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

    List<String> getPicturesForExperiment(Experiment data);
    List<String> getPicturesForComment(Comment data);
}