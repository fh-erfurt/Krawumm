package de.joemiagroup.krawumm.repositories.materials;

import de.joemiagroup.krawumm.domains.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * This interface declares methods for the MaterialRepositoryImpl
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

@RepositoryRestResource(collectionResourceRel = "materials", path = "materials")
public interface MaterialRepository extends JpaRepository<Material, Long> {
    void deleteMaterialById(Long id);

    List<Material> findMaterialByName(String name);
    Material getLastInsertedMaterial();
}
