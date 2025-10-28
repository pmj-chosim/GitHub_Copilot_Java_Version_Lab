package com.example.demo.service;
import com.example.demo.repository.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        Todo newTodo = new Todo();
        newTodo.setTitle(title);
        newTodo.setCompleted(false); // 새로운 할 일은 항상 '미완료'로 시작
        return todoRepository.save(newTodo);
    }
}