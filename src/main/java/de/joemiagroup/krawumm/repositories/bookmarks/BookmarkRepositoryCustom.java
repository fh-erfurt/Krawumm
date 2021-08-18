package de.joemiagroup.krawumm.repositories.bookmarks;

import de.joemiagroup.krawumm.domains.Bookmark;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

public interface BookmarkRepositoryCustom {
    List<Bookmark> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

}