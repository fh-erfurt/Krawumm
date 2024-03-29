package de.joemiagroup.krawumm.repositories.comments;

import de.joemiagroup.krawumm.domains.Comment;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for the CommentRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

public interface CommentRepositoryCustom {
    List<Comment> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);
    List<Comment> getAllComments();

}