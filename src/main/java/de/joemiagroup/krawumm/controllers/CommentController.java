package de.joemiagroup.krawumm.controllers;

import de.joemiagroup.krawumm.domains.Comment;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity save(@RequestBody Comment comment){
        if(Objects.nonNull(comment.getId())){
            return ResponseEntity.badRequest().body("Method is not allowed for already persisted Entities.");
        }
        return ResponseEntity.ok(this.commentRepository.save(comment));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Comment> update(@RequestBody Comment comment){
        return ResponseEntity.ok(this.commentRepository.save(comment));
    }

    @DeleteMapping("/{id}")
    void deleteComment(@PathVariable(value = "id") long id){
        this.commentRepository.deleteById(id);
    }
}
