package de.joemiagroup.krawumm.repositories.comments;

import de.joemiagroup.krawumm.domains.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * This interface declares a delete method
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentById(Long id);
    List<Comment> getAllComments();

}
