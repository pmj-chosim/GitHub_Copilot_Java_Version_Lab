# Spring Boot Todo 애플리케이션 만들기 (구조 가이드)

Spring Initializr를 사용하여 프로젝트를 생성하고, 요청하신 구조에 맞춰 간단한 Todo 애플리케이션을 만드는 단계별 가이드입니다.

---

### ## 1단계: Spring Initializr로 프로젝트 생성 🚀

[**start.spring.io**](https://start.spring.io/)에 접속하여 아래와 같이 프로젝트를 설정하고 생성합니다.

* **Project**: `Maven`
* **Language**: `Java`
* **Spring Boot**: `3.3.4` (또는 최신 안정 버전)
* **Project Metadata**:
    * Group: `com.example`
    * Artifact: `demo`
    * Java: `17`
* **Dependencies**:
    * `Spring Web`
    * `Lombok`
    * `Spring Test`

**GENERATE** 버튼을 클릭하여 zip 파일을 다운로드하고, 압축을 푼 뒤 IDE(IntelliJ, VS Code 등)에서 프로젝트를 엽니다.

---

### ## 2단계: 패키지 및 파일 구조 만들기 📂

IDE에서 `src/main/java/com/example/demo` 경로 아래에 `controller`, `repository`, `service` 패키지와 필요한 클래스 파일들을 생성합니다.

[파일 구조 생략]

---

### ## 3단계: 코드 작성하기 💻

생성한 각 파일에 역할을 정의하고 코드를 작성합니다.

#### **repository/Todo.java** (데이터 모델)

```bash
src
└── main
    └── java
        └── com
            └── example
                └── demo
                    ├── DemoApplication.java  (이미 생성되어 있음)
                    ├── controller
                    │   └── TodoController.java (새로 만들기)
                    ├── repository
                    │   ├── Todo.java         (새로 만들기)
                    │   └── TodoRepository.java (새로 만들기)
                    └── service
                        └── TodoService.java    (새로 만들기)
```

#### **repository/Todo.java** (데이터 모델)  
```java
package com.example.demo.repository;
import lombok.Data;

@Data
public class Todo {
    private String title;
    private boolean completed;
}
```


#### **repository/TodoRepository.java** (데이터 저장소 역할)

```
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
```

#### **service/TodoService.java** (비즈니스 로직)

```java
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
}
```

#### **controller/TodoController.java** (API 엔드포인트)

```java
package com.example.demo.controller;

import com.example.demo.repository.Todo;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
```

---

### ## 4단계: 애플리케이션 실행하기 ▶️

`DemoApplication.java`의 `main` 메소드를 실행하여 Spring Boot 애플리케이션을 시작합니다.

---

### ## 5단계: 결과 확인하기 🎯

애플리케이션이 실행된 후, 웹 브라우저나 API 테스트 도구에서 아래 URL로 GET 요청을 보냅니다.

**URL**: `http://localhost:8080/api/todos`

요청이 성공하면 아래와 같은 JSON 응답을 확인할 수 있습니다.

```json
[
    {
        "title": "운동하기",
        "completed": false
    },
    {
        "title": "공부하기",
        "completed": true
    }
]
```
