# 4.GitHub Copilot을 활용한 유닛(단위) 테스트 개발
### GitHub Copilot을 사용한 단위 테스트 개발

GitHub Copilot은 복잡한 프레임워크 기반의 프로젝트에서 테스트 코드를 작성하는 과정을 크게 가속화합니다. 이 연습에서는 Spring Boot 프로젝트에 대한 단위 테스트를 개발하는 방법을 배웁니다.  

#### 시나리오

당신은 To-Do 앱 프로젝트를 맡은 개발자입니다. 코드는 이미 완성되었지만, 중요한 기능에 대한 단위 테스트가 부족합니다. GitHub Copilot의 도움을 받아 `TodoService` 클래스의 핵심 기능에 대한 단위 테스트를 작성해야 합니다.  

#### 실습 준비
이번 모듈 4와 모듈 5에서는 Java 17 버전으로 작성된 Spring Boot 앱 예제 코드를 사용합니다. 

* Java 17 버전이 없다면? 
> 컨테이너를 사용해 실행 가능하므로 꼭 설치하지 않아도 되지만, IntelliJ에서 설치할 수 있습니다.  
> `File` > `Project Structure` > `SDK` > `Download JDK..` 를 선택합니다.  <br>   
> <img width="980" height="627" alt="image" src="https://github.com/user-attachments/assets/20d23ada-fb26-4ac0-bdfe-d59195366dcb" />  
> Java 버전을 17로 선택하고, 선호하는 벤더사를 선택해주시면 됩니다. 선호하는 벤더사가 없다면 `Microsoft OpenSDK`로 선택해주세요.    
> <img width="568" height="539" alt="image" src="https://github.com/user-attachments/assets/88170831-485d-46db-9c72-4f9a8962141a" />  
  
  

터미널창(git bash)에 현재 GitHub 레포지토리 내용을 clone합니다. 
> 또는 직접 베이스 코드를 붙여 넣어서 만드실 수 있습니다. [모듈4 Spring Boot 베이스 코드 만들기](https://github.com/pmj-chosim/GitHub_Copilot_Java_Version_Lab/blob/main/ModuleReadMe/MoreAboutModule4.md)
```bash
git clone https://github.com/pmj-chosim/GitHub_Copilot_Java_Version_Lab.git
```
<img width="938" height="47" alt="image" src="https://github.com/user-attachments/assets/06f95dec-247c-4094-afc6-664fb36300d8" />  

IntelliJ에서 `File` > `Open`을 통해 방금 clone한 폴더를 열어 주세요.    

개발하기 전 코드가 잘 실행되는 지 먼저 확인합니다.  

터미널 창을 열어, 현재 복사된 코드가 있는 경로에서 docker 명령어를 통해 코드를 실행합니다.  
<img width="1607" height="491" alt="image" src="https://github.com/user-attachments/assets/1d525dd7-fee4-419d-ac72-825950007357" />  

```bash
docker build -t my-java-app .
```
```bash
docker run --rm -p 8080:8080 my-java-app
```   

컨테이너가 잘 실행되고 나면
<img width="655" height="206" alt="image" src="https://github.com/user-attachments/assets/65cb1d9e-a682-4b02-ae02-4e7541505bb2" />  
http://localhost:8080/api/todos 에서 예제 코드가 작동하는 것을 확인할 수 있습니다.  
    

---

### 단계별 실습 가이드

#### 1단계: Ask 모드로 단위 테스트 접근 방식 살펴보기

먼저 코파일럿과 함께 Spring Boot 프로젝트에 대한 테스트 접근 방식을 분석해 봅시다.

1.  IntelliJ에서 **코파일럿 채팅창**을 열고 모드가 `Ask`로 되어있는지 확인합니다.
2.  채팅창에 `@workspace 이 Spring Boot 프로젝트에 대한 단위 테스트는 어떻게 구성하는 것이 좋을까?`를 입력하여 테스트 전략을 물어보세요.

#### 2단계: Agent 모드로 테스트 클래스 만들기

`Agent` 모드를 사용하면 코파일럿이 자율적으로 폴더를 만들고 파일을 추가하는 등 복잡한 작업을 처리합니다.

1.  IntelliJ에서 코파일럿 채팅창을 열고 모드를 `Agent`로 변경합니다.
2.  채팅창에 아래 프롬프트를 입력하여 `TodoService`를 테스트할 파일을 자동으로 생성하도록 요청하세요.
    * `/tests test 폴더의 com.example.demo.service 패키지 안에 TodoServiceTest.java 파일을 생성해줘. 파일에 JUnit 5와 Mockito 테스트 클래스 스텁을 추가해줘.`
3.  `Agent`가 작업을 완료하면 생성된 `TodoServiceTest.java` 파일을 확인합니다.

#### 3단계: 인라인 채팅으로 Service 단위 테스트 작성하기

이 테스트는 TodoService.java 파일을 수정하는 것이 아니라, `src/test/java/com/example/demo/service` 폴더에 있는 `TodoServiceTest.java` 파일에 작성합니다.


1.  (2단계에서) Agent가 생성했거나, 혹은 직접 만든 `src/test/java/com/example/demo/service/TodoServiceTest.java` 파일을 엽니다.
2. 파일이 비어있다면, 먼저 Mockito를 사용하기 위한 테스트 기본 구조를 복사하여 붙여넣습니다.
```bash
package com.example.demo.service;

import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // Mockito 사용 선언
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository; // 가짜 Repository

    @InjectMocks
    private TodoService todoService; // 테스트 대상 Service (가짜 Repo 주입)

    // 3단계: Copilot을 호출할 커서를 여기에 둡니다.

}
```
<img width="1497" height="772" alt="image" src="https://github.com/user-attachments/assets/0ea46c7d-c729-4fcc-a61c-8afa96bab409" />

3. 방금 붙여넣은 코드에서 // 3단계: ... 주석이 있는 곳에 커서를 둡니다.  

4. 마우스 우클릭 > GitHub Copilot > Copilot: Open Inline Chat을 눌러 인라인 채팅을 활성화합니다.  

5. 채팅창에 아래와 같이 구체적인 프롬프트를 입력합니다. (BDDMockito 스타일을 명시하면 'given-when-then' 형식의 깔끔한 코드를 제안해 줍니다.)  
  addTodo 메서드에 대한 단위 테스트를 BDDMockito 스타일로 만들어줘.

  > "새 할일"이라는 title이 주어졌을 때,
  > repository.save가 any(Todo.class)로 호출되면, title이 "새 할일"이고 completed가 false인 Todo 객체를 반환하도록 given (stub) 처리해줘.
  > service.addTodo가 "새 할일"로 호출되면 (when)
  > 반환된 Todo 객체가 null이 아니고, title이 "새 할일", completed가 false인지 assert 해줘.
  > 마지막으로 repository.save가 1번 호출되었는지 verify 해줘.

6. 코파일럿이 위 요구사항에 맞는 테스트 메서드 코드를 제안하면, Accept를 눌러 코드를 삽입합니다.  

7. (결과 확인) 성공적으로 코드가 삽입되면 TodoServiceTest.java 파일은 다음과 같은 모습이 됩니다. IntelliJ의 Run 버튼(메서드 옆 녹색 삼각형)을 눌러 테스트가 통과(PASS)하는지 확인합니다.  


코드가 성공적으로 작동되지 않으면 채팅을 통해 트러블슈팅을 합니다. 아래와 같은 예시 코드가 나올 수 있도록 프롬프트를 통해 수정해 나갑니다.  

예시 코드:
```bash
package com.example.demo.service;

import com.example.demo.repository.Todo;
import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    @DisplayName("새로운 할 일을 추가하면 저장되고, completed는 false여야 한다")
    void addTodoTest() {
        // given (준비)
        String title = "새 할일";
        Todo todoToSave = new Todo();
        todoToSave.setTitle(title);
        todoToSave.setCompleted(false);

        // todoRepository.save(any(Todo.class))가 호출되면, todoToSave 객체를 반환하라고 설정
        given(todoRepository.save(any(Todo.class))).willReturn(todoToSave);

        // when (실행)
        Todo result = todoService.addTodo(title);

        // then (검증)
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.isCompleted()).isFalse();

        // todoRepository.save가 1번 호출되었는지 검증
        then(todoRepository).should().save(any(Todo.class));
    }
}
```

#### 4단계: 인라인 채팅으로 Controller 단위 테스트 작성하기  
이제 API의 '창구' 역할을 하는 `TodoController`를 테스트할 차례입니다. 컨트롤러 테스트는 MockMvc라는 도구를 사용해, 마치 브라우저나 Postman이 API를 호출하는 것처럼 시뮬레이션합니다.

1. 테스트 파일 생성: `src/test/java/com/example/demo/controller` 경로(폴더)에 `TodoControllerTest.java` 파일을 생성합니다.

2. 테스트 기본 구조 붙여넣기: `TodoControllerTest.java` 파일을 열고, `MockMvc` 테스트에 필요한 기본 구조를 붙여넣습니다.  
```bash
package com.example.demo.controller;

import com.example.demo.repository.Todo;
import com.example.demo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper; // JSON 처리를 위해 추가
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType; // MimeType을 위해 추가
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class) // Controller 레이어만 테스트
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc; // 웹 요청 시뮬레이터

    @MockBean
    private TodoService todoService; // 가짜 Service

    @Autowired
    private ObjectMapper objectMapper; // 객체 <-> JSON 변환기

    // 4단계: Copilot을 호출할 커서를 여기에 둡니다.
}
```
3. `getTodos` (GET 요청) 테스트 코드 생성: `// 4단계: ...` 주석 아래에 커서를 두고, 인라인 채팅(Ctrl+I 또는 Cmd+I)을 열어 요청합니다.  
  getTodos 메서드에 대한 테스트 코드를 BDDMockito 스타일로 만들어줘.  
  >"운동하기", "공부하기"가 포함된 Todo 리스트를 준비해 줘.  
  >todoService.getTodos()가 호출되면(given) 이 리스트를 반환하도록 설정해 줘.  
  >mockMvc.perform으로 /api/todos에 GET 요청을 보냈을 때(when),  
  >상태 코드가 isOk()이고, JSON 응답의 $[0].title이 "운동하기"인지 검증(then)해줘.  

Copilot이 제안한 코드를 수락합니다.  

4. `createTodo` (POST 요청) 테스트 코드 생성: 방금 생성된 `getTodosTest()` 메서드 아래에 커서를 두고, 다시 인라인 채팅을 열어 요청합니다.  

이번엔 `createTodo` 메서드 테스트 코드를 만들어줘.  
> "새 할일"이라는 title을 가진 Todo 객체를 준비해 줘.  
> todoService.addTodo("새 할일")이 호출되면(given) 준비한 Todo 객체를 반환하도록 설정해 줘.  
> mockMvc.perform으로 /api/todos에 POST 요청을 보낼 거야. 요청 본문(content)은 objectMapper로 직렬화하고 contentType은 APPLICATION_JSON으로 설정해 줘.  
> 상태 코드가 isCreated()이고, JSON 응답의 title이 "새 할일"인지 검증(then)해줘.  
 
Copilot이 제안한 코드를 수락합니다.  

5. (결과 확인) 모든 코드를 수락하면 TodoControllerTest.java 파일은 다음과 비슷한  모습이 됩니다. IntelliJ에서 Run 버튼을 눌러 2개의 테스트가 모두 통과하는지 확인합니다.

```bash
package com.example.demo.controller;

import com.example.demo.repository.Todo;
import com.example.demo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/todos 요청 시 모든 할 일 목록을 반환한다")
    void getTodosTest() throws Exception {
        // given
        Todo todo1 = new Todo();
        todo1.setTitle("운동하기");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setTitle("공부하기");
        todo2.setCompleted(true);
        List<Todo> todos = Arrays.asList(todo1, todo2);

        given(todoService.getTodos()).willReturn(todos);

        // when & then
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("운동하기"))
                .andExpect(jsonPath("$[1].title").value("공부하기"));
    }

    @Test
    @DisplayName("POST /api/todos 요청 시 새 할 일을 생성한다")
    void createTodoTest() throws Exception {
        // given
        Todo todoToCreate = new Todo();
        todoToCreate.setTitle("새 할일");

        Todo savedTodo = new Todo();
        savedTodo.setTitle("새 할일");
        savedTodo.setCompleted(false);

        given(todoService.addTodo("새 할일")).willReturn(savedTodo);

        // when & then
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoToCreate))) // 객체를 JSON 문자열로 변환
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("새 할일"))
                .andExpect(jsonPath("$.completed").value(false));
    }
}
```
  

