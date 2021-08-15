package de.joemiagroup.krawumm.repositories.materials;

import de.joemiagroup.krawumm.domains.Material;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

public interface MaterialRepositoryCustom {
    List<Material> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);
}