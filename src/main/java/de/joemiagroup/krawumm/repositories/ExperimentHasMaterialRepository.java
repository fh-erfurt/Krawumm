package de.joemiagroup.krawumm.repositories;

import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "experimentHasMaterials", path = "experimentHasMaterials")
public interface ExperimentHasMaterialRepository extends JpaRepository<ExperimentHasMaterial, Long> {

}
