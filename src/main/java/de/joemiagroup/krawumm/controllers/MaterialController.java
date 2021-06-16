package de.joemiagroup.krawumm.controllers;

import de.joemiagroup.krawumm.domains.Material;
import de.joemiagroup.krawumm.storages.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialController(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody Material material){
        if(Objects.nonNull(material.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.materialRepository.save(material));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Material> update(@RequestBody Material material){
        return ResponseEntity.ok(this.materialRepository.save(material));
    }

    @DeleteMapping("/{id}")
    void deleteMaterial(@PathVariable(value = "id") long id){
        this.materialRepository.deleteById(id);
    }
}
