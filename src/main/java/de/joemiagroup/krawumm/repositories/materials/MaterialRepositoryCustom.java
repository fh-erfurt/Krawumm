package de.joemiagroup.krawumm.repositories.materials;

import de.joemiagroup.krawumm.domains.Material;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

/**
 * This interface declares methods for the MaterialRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

public interface MaterialRepositoryCustom {
    List<Material> findByParameters(int page, int count, Map<String, FilterMeta> filters, Map<String, SortMeta> sorts);

    long countByParameters(Map<String, FilterMeta> filters);

    List<Material> findMaterialByName(String name);
    Material getLastInsertedMaterial();
    List<Material> getAllMaterials();
    Material getMaterialById(long id);
}