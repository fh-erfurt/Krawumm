package de.joemiagroup.krawumm.repositories.pictures;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pictures", path = "pictures")
public interface PicturesRepository  extends JpaRepository<Pictures, Long>, PicturesRepositoryCustom {
    void deletePicturesById(Long id);

}
