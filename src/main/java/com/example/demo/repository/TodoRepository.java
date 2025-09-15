package com.example.demo.repository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    public TodoRepository() {
        // 임시 데이터
        Todo todo1 = new Todo();
        todo1.setTitle("운동하기");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setTitle("공부하기");
        todo2.setCompleted(true);

        todos.add(todo1);
        todos.add(todo2);
    }

    public List<Todo> findAll() {
        return todos;
    }
}