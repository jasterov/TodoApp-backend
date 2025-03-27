package com.example.todoapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTodoSucceedsWithValidTitle() throws Exception {
        String todoJson = """
        {
          "title": "Validní úkol",
          "description": "Test popis",
          "completed": false
        }
        """;

        mockMvc.perform(
                        post("/api/todos")
                                .contentType("application/json")
                                .content(todoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Validní úkol"));
    }

    @Test
    public void testCreateTodoFailsWhenTitleIsBlank() throws Exception {
        String todoJson = """
        {
          "title": " ",
          "description": "Prázdný titulek",
          "completed": false
        }
        """;

        mockMvc.perform(
                        post("/api/todos")
                                .contentType("application/json")
                                .content(todoJson))
                .andExpect(status().isBadRequest());
    }

}
