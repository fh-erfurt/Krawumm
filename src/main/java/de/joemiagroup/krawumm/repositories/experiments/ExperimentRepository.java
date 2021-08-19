package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.domains.Experiment;

import de.joemiagroup.krawumm.domains.TrueFalse;
import de.joemiagroup.krawumm.web.experiments.FilterView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.*;

@RepositoryRestResource(collectionResourceRel = "experimernts", path = "experimernts")
public interface ExperimentRepository extends JpaRepository<Experiment, Long>, ExperimentRepositoryCustom {
    void deleteExperimentById(Long id);

    List<Experiment> getAllExperiments(TrueFalse t);
    List<Experiment> useFilterOnExperiment(FilterView filters);
}
