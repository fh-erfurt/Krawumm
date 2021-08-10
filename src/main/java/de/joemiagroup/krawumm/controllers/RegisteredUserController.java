/*
package de.joemiagroup.krawumm.controllers;


import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/registerdUsers")
public class RegisteredUserController {

    private final RegisteredUserRepository registeredUserRepository;

    @Autowired
    public RegisteredUserController(RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody RegisteredUser registeredUser){
        if(Objects.nonNull(registeredUser.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.registeredUserRepository.save(registeredUser));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<RegisteredUser> update(@RequestBody RegisteredUser registeredUser){
        return ResponseEntity.ok(this.registeredUserRepository.save(registeredUser));
    }

    @DeleteMapping("/{id}")
    void deleteRegisteredUser(@PathVariable(value = "id") long id){
        this.registeredUserRepository.deleteById(id);
    }
}
*/
