package de.joemiagroup.krawumm.storages;

//import de.joemiagroup.krawumm.core.H2Controller;
import de.joemiagroup.krawumm.domains.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "experimernts", path = "experimernts")
public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

}
