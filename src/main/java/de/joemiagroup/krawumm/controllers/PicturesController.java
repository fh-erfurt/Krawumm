package de.joemiagroup.krawumm.controllers;


import de.joemiagroup.krawumm.domains.Pictures;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/pictures")
public class PicturesController {

    private final PicturesRepository picturesRepository;

    @Autowired
    public PicturesController(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody Pictures picture){
        if(Objects.nonNull(picture.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.picturesRepository.save(picture));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Pictures> update(@RequestBody Pictures picture){
        return ResponseEntity.ok(this.picturesRepository.save(picture));
    }

    @DeleteMapping("/{id}")
    void deletePicture(@PathVariable(value = "id") long id){
        this.picturesRepository.deleteById(id);
    }
}
