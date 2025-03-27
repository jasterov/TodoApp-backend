package com.example.todoapp.repository;

import com.example.todoapp.model.TodoEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntry, Long> {
}
