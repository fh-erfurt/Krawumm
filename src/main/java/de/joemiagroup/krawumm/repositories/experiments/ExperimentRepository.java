package de.joemiagroup.krawumm.repositories.experiments;

import de.joemiagroup.krawumm.domains.Experiment;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import java.util.*;
import java.util.stream.Collectors;

@RepositoryRestResource(collectionResourceRel = "experimernts", path = "experimernts")
public interface ExperimentRepository extends JpaRepository<Experiment, Long>, ExperimentRepositoryCustom {
    void deleteExperimentById(Long id);
}
