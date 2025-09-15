### GitHub Copilot을 사용하여 기존 코드 리팩토링

GitHub Copilot은 기존 코드의 품질, 성능, 가독성, 유지보수성을 개선하는 데 도움을 줍니다. 이 연습에서는 GitHub Copilot을 사용하여 기존 자바 Spring Boot 애플리케이션의 특정 부분을 리팩터링하고 개선합니다.

#### 시나리오

당신은 To-Do 앱 프로젝트의 코드 품질 검토를 맡게 되었습니다. 기존 코드의 가독성과 성능을 개선해야 하는 과제가 주어졌습니다. GitHub Copilot의 도움을 받아 잠재적인 코드 문제를 파악하고 수정해야 합니다.

#### 실습 준비

IntelliJ에서 이전 Module4에서 사용한 Spring Boot 프로젝트가 열려 있는지 확인하세요.

---

### 단계별 실습 가이드

#### 1단계: Ask 모드로 코드 분석 및 리팩터링 계획하기

먼저 현재 코드베이스를 분석하고 리팩터링할 부분을 찾아봅시다.

1.  IntelliJ에서 **코파일럿 채팅창**을 열고 모드가 **`Ask`**로 되어있는지 확인합니다.
2.  `repository/TodoRepository.java` 파일을 엽니다.
3.  채팅창에 `@file TodoRepository.java 이 파일에서 성능을 개선할 수 있는 부분을 찾아줘`라고 입력해 코드 분석을 요청하세요.

---

#### 2단계: 스마트 액션과 인라인 채팅으로 코드 개선하기

이제 파악한 내용을 바탕으로 코드를 직접 개선해 봅시다.

1.  `repository/TodoRepository.java` 파일의 `findAll` 메서드를 Stream API를 사용하지 않는 `foreach` 루프로 변경하세요.
    * 예를 들어, `return todos;`를 `List<Todo> allTodos = new ArrayList<>(); for (Todo todo : todos) { allTodos.add(todo); } return allTodos;`로 바꿔줍니다.
2.  변경한 코드 블록에 마우스를 가져가면, **`Fix This`**라는 스마트 액션이 나타날 수 있습니다. 이를 클릭하여 코드를 자동으로 수정해 보세요.
3.  만약 `Fix This`가 나타나지 않으면, 코드를 선택하고 **인라인 채팅**(`Ctrl + I` 또는 `Cmd + I`)을 연 후 `/fix 이 코드를 더 효율적으로 개선해줘`라고 입력하세요.
    * **설명**: `/fix`는 **슬래시 명령어**로, 선택한 코드의 문제점을 찾고 수정할 것을 요청합니다.

* `#file README.md "설명" 섹션에 "이 API는 Spring Boot를 활용해 할 일 목록을 JSON 형식으로 제공합니다."라고 추가해줘.`라고 입력합니다.
    * **설명**: `#file`은 **채팅 변수**로, 특정 파일을 지정해 작업을 요청할 수 있습니다. `Edit` 모드는 이 파일에만 작업을 수행합니다.
