package de.joemiagroup.krawumm.storages;

//import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.domains.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
