package com.example.demo.repository;
public class Todo {
    private String title;
    private boolean completed;

    // 1. Jackson이 JSON -> 객체 변환 시 필요한 기본 생성자
    public Todo() {
    }

    // 2. Controller에서 newTodoRequest.getTitle()을 위해 필요한 Getter
    public String getTitle() {
        return title;
    }

    // 3. Jackson이 JSON의 "title" 값을 객체에 설정하기 위해 필요한 Setter
    public void setTitle(String title) {
        this.title = title;
    }

    // 4. 나머지 Getter/Setter
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}