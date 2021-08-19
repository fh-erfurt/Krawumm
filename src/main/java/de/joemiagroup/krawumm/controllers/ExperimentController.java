package de.joemiagroup.krawumm.controllers;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * This class is the controller for experiments
 * <br>
 *
 * @author Jessica Eckardtsberg
 *
 */

@Transactional
@RestController
@RequestMapping("/experiments")
public class ExperimentController {

    private final ExperimentRepository experimentRepository;

    @Autowired
    public ExperimentController(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody Experiment experiment){
        if(Objects.nonNull(experiment.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.experimentRepository.save(experiment));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Experiment> update(@RequestBody Experiment experiment){
        return ResponseEntity.ok(this.experimentRepository.save(experiment));
    }

    @DeleteMapping("/{id}")
    void deleteExperiment(@PathVariable(value = "id") long id){
        this.experimentRepository.deleteById(id);
    }
}
