package de.joemiagroup.krawumm.repositories;

import de.joemiagroup.krawumm.domains.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "materials", path = "materials")
public interface MaterialRepository extends JpaRepository<Material, Long> {

}