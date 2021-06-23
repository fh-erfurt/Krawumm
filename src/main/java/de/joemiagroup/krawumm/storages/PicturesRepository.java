package de.joemiagroup.krawumm.storages;

//import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.domains.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pictures", path = "pictures")
public interface PicturesRepository  extends JpaRepository<Pictures, Long> {

}
