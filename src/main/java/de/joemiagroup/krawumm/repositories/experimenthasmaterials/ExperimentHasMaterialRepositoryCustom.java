package de.joemiagroup.krawumm.repositories.experimenthasmaterials;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

public interface ExperimentHasMaterialRepositoryCustom {
    List<ExperimentHasMaterial> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);
}