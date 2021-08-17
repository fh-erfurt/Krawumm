package de.joemiagroup.krawumm.repositories.experimenthasmaterials;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "experimentHasMaterials", path = "experimentHasMaterials")
public interface ExperimentHasMaterialRepository extends JpaRepository<ExperimentHasMaterial, Long> {
    void deleteExperimentHasMaterialById(long Id);
}