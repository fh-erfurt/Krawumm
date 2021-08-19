package de.joemiagroup.krawumm.controllers;

import de.joemiagroup.krawumm.domains.Instruction;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * This class is the controller for instructions
 * <br>
 *
 * @author Jessica Eckardtsberg
 *
 */

@Transactional
@RestController
@RequestMapping("/instructions")
public class InstructionController {

    private final InstructionRepository instructionRepository;

    @Autowired
    public InstructionController(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody Instruction instruction){
        if(Objects.nonNull(instruction.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.instructionRepository.save(instruction));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Instruction> update(@RequestBody Instruction instruction){
        return ResponseEntity.ok(this.instructionRepository.save(instruction));
    }

    @DeleteMapping("/{id}")
    void deleteInstruction(@PathVariable(value = "id") long id){
        this.instructionRepository.deleteById(id);
    }
}
