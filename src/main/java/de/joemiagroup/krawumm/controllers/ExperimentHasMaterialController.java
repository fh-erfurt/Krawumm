package de.joemiagroup.krawumm.controllers;


import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
import de.joemiagroup.krawumm.repositories.experimenthasmaterials.ExperimentHasMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * This class is the controller for experiment_has_material
 * <br>
 *
 * @author Jessica Eckardtsberg
 *
 */

@Transactional
@RestController
@RequestMapping("/experimentHasMaterials")
public class ExperimentHasMaterialController {

    private final ExperimentHasMaterialRepository experimentHasMaterialsRepository;

    @Autowired
    public ExperimentHasMaterialController(ExperimentHasMaterialRepository experimentHasMaterialsRepository) {
        this.experimentHasMaterialsRepository = experimentHasMaterialsRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody ExperimentHasMaterial experimentHasMaterial){
        if(Objects.nonNull(experimentHasMaterial.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.experimentHasMaterialsRepository.save(experimentHasMaterial));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<ExperimentHasMaterial> update(@RequestBody ExperimentHasMaterial comment){
        return ResponseEntity.ok(this.experimentHasMaterialsRepository.save(comment));
    }

    @DeleteMapping("/{id}")
    void deleteExperimentHasMaterial(@PathVariable(value = "id") long id){
        this.experimentHasMaterialsRepository.deleteById(id);
    }
}
