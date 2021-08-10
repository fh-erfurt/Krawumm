package de.joemiagroup.krawumm.controllers;


import de.joemiagroup.krawumm.domains.Rating;
import de.joemiagroup.krawumm.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody Rating rating){
        if(Objects.nonNull(rating.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.ratingRepository.save(rating));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Rating> update(@RequestBody Rating rating){
        return ResponseEntity.ok(this.ratingRepository.save(rating));
    }

    @DeleteMapping("/{id}")
    void deleteRating(@PathVariable(value = "id") long id){
        this.ratingRepository.deleteById(id);
    }
}
