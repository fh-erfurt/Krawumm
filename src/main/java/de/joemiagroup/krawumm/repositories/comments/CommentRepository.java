package de.joemiagroup.krawumm.repositories.comments;

import de.joemiagroup.krawumm.domains.Comment;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.web.experiments.ExperimentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentById(Long id);

}
