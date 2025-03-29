package com.example.todoapp.controller;

import com.example.todoapp.model.TodoEntry;
import com.example.todoapp.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") //pro lokalni vyvoj
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public Page<TodoEntry> getTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable Long id) {
        return todoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodoEntry create(@Valid @RequestBody TodoEntry todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoEntry> update(@PathVariable Long id, @Valid @RequestBody TodoEntry todo) {
        return todoRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(todo.getTitle());
                    existing.setDescription(todo.getDescription());
                    existing.setCompleted(todo.isCompleted());
                    return ResponseEntity.ok(todoRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
