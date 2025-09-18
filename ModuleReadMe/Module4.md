# 4.GitHub Copilot을 활용한 유닛(단위) 테스트 개발
### GitHub Copilot을 사용한 단위 테스트 개발

GitHub Copilot은 복잡한 프레임워크 기반의 프로젝트에서 테스트 코드를 작성하는 과정을 크게 가속화합니다. 이 연습에서는 Spring Boot 프로젝트에 대한 단위 테스트를 개발하는 방법을 배웁니다.  

#### 시나리오

당신은 To-Do 앱 프로젝트를 맡은 개발자입니다. 코드는 이미 완성되었지만, 중요한 기능에 대한 단위 테스트가 부족합니다. GitHub Copilot의 도움을 받아 `TodoService` 클래스의 핵심 기능에 대한 단위 테스트를 작성해야 합니다.  

#### 실습 준비
이번 모듈 4와 모듈 5에서는 Java 17 버전으로 작성된 Spring Boot 앱 예제 코드를 사용합니다.  

* Java 17 버전이 없다면?
> IntelliJ에서 설치할 수 있습니다.
> `File` > `Project Structure` > `SDK` > `Download JDK..` 를 선택합니다.  <br>  
> <img width="980" height="627" alt="image" src="https://github.com/user-attachments/assets/20d23ada-fb26-4ac0-bdfe-d59195366dcb" />
> Java 버전을 17로 선택하고, 선호하는 벤더사를 선택해주시면 됩니다. 선호하는 벤더사가 없다면 `Microsoft OpenSDK`로 선택해주세요.  
> <img width="568" height="539" alt="image" src="https://github.com/user-attachments/assets/88170831-485d-46db-9c72-4f9a8962141a" />
  
  

터미널창(git bash)에 현재 GitHub 레포지토리 내용을 clone합니다. 
> 또는 직접 베이스 코드를 붙여 넣어서 만드실 수 있습니다. [모듈4 Spring Boot 베이스 코드 만들기]()
```bash
git clone https://github.com/pmj-chosim/GitHub_Copilot_Java_Version_Lab.git
```
<img width="938" height="47" alt="image" src="https://github.com/user-attachments/assets/06f95dec-247c-4094-afc6-664fb36300d8" />  

IntelliJ에서 `File` > `Open`을 통해 방금 clone한 폴더를 열어 주세요.    
    

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
    * `/tests test 폴더의 com.example.demo 패키지 안에 TodoServiceTest.java 파일을 생성해줘. 파일에 JUnit 5 테스트 클래스 스텁을 추가해줘.`
3.  `Agent`가 작업을 완료하면 생성된 `TodoServiceTest.java` 파일을 확인합니다.

#### 3단계: 인라인 채팅으로 테스트 코드 작성하기

`TodoServiceTest.java` 파일에 대한 테스트 코드를 작성해 봅시다.

1.  `TodoServiceTest.java` 파일을 열고 클래스 본문 안에 커서를 둡니다.
2.  `마우스 우클릭` > `GitHub Copilot` > `Copilot: Open Inline Chat`을 눌러 **인라인 채팅**을 활성화합니다.
3.  채팅창에 `addTodo 메서드에 대한 단위 테스트를 만들어줘. "새로운 할 일을 추가하면 목록 크기가 1 증가해야 한다"는 것을 확인해줘.`와 같이 요청합니다.
4.  코파일럿이 제안하는 코드를 받아들여 메서드를 완성합니다.

#### 4단계: 스마트 액션으로 테스트 코드 제안받기

이번에는 **스마트 액션**을 사용해 `TodoController`에 대한 테스트 코드를 제안받아 봅시다.

1.  `.\src\test\java\com\example\demo` 경로에 `TodoControllerTest.java` 파일을 만듭니다.  <br>  
<img width="1060" height="633" alt="image" src="https://github.com/user-attachments/assets/6cee008c-562e-43d6-aee3-fde3b40eeac0" />  <br>  
2.  `TodoController.java` 파일을 엽니다.
3.  `getTodos()` 메서드를 마우스로 드래그해서 선택하세요.
4.  마우스 우클릭 > `GitHub Copilot` > `Generate Tests`를 선택합니다.
5.  코파일럿이 채팅창에 테스트 코드 스니펫을 제안하면, 이를 복사하여 `TodoControllerTest.java` 붙여 넣습니다.
  
GitHub Copilot이 완벽하게 잘 작동하는 코드를 주지 않을 수 있습니다. GitHub Copilot이 제공한 코드를 채팅창에서 질문해 먼저 이해합니다.  

코드 실행이 원활하지 않을 경우, Copilot에게 “왜 실패했는지 설명해줘”라고 요청할 수 있습니다.  
이후, GitHub Copilot이 제안한 코드에서 일부만 수용을 하거나 채팅 등을 통해 수정해 나갑니다.  
  

