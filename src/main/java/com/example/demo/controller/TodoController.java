package com.example.demo.controller;

import com.example.demo.repository.Todo;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 성공 시 201 Created 상태 코드 반환
    public Todo createTodo(@RequestBody Todo newTodoRequest) {
        // 요청 본문(JSON)으로 받은 Todo 객체에서 title을 꺼내 서비스로 전달
        return todoService.addTodo(newTodoRequest.getTitle());
    }
}