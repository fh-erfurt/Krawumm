package de.joemiagroup.krawumm.repositories.bookmarks;

import de.joemiagroup.krawumm.domains.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This interface declares a delete method
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */


@RepositoryRestResource(collectionResourceRel = "bookmarks", path = "bookmarks")
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    void deleteBookmarkById(Long id);

}
