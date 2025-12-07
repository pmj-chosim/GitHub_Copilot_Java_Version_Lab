# 2.GitHub Copilot을 활용한 설명 및 문서화
### 시나리오

당신은 팀의 신규 프로젝트를 맡게 되었습니다. 동료가 남겨둔 것은 기본적인 파일 구조와 이미 완성된 코드뿐입니다.   
당신의 임무는 GitHub Copilot의 도움을 받아 이 코드를 분석하고, 완벽하게 문서화하는 것입니다.  

#### 실습 준비  
IntelliJ에서 `File` > `New` > `Project` 를 선택해 새 프로젝트를 만들어 줍니다.  
Java 버전은 17버전 사용을 권장드리나, 다른 버전을 사용해도 무관합니다.  
<br>  
<img width="952" height="606" alt="image" src="https://github.com/user-attachments/assets/b0121be7-9944-41ae-96c6-2e5afeca110e" />  
<br>  
module2 이름과 샘플 코드 없이 프로젝트를 생성합니다.  
  
<img width="388" height="117" alt="image" src="https://github.com/user-attachments/assets/4d05f056-5383-45fc-a8a7-4e5830f3893e" />  
<br>  
  
`src` 폴더에 `Main.java`, `Todo.java`, `TodoListManager.java` 세 클래스 파일을 만듭니다.  

`Main.java` 클래스 파일에 다음 내용을 붙여 넣습니다.  
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TodoListManager manager = new TodoListManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("간단한 To-Do 리스트 앱을 시작합니다.");

        while (running) {
            System.out.println("\n--- 옵션을 선택하세요 ---");
            System.out.println("1. 할 일 추가");
            System.out.println("2. 할 일 목록 보기");
            System.out.println("3. 할 일 완료하기");
            System.out.println("4. 종료");
            System.out.print("선택: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                        System.out.print("추가할 할 일 제목을 입력하세요: ");
                        String title = scanner.nextLine();
                        manager.addTodo(title);
                    break;
                case "2":
                    manager.listTodos();
                    break;
                case "3":
                    System.out.print("완료할 할 일의 번호를 입력하세요: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    manager.completeTodo(index);
                    break;
                case "4":
                    running = false;
                    System.out.println("To-Do 리스트 앱을 종료합니다.");
                    break;
                default:
                    System.out.println("유효하지 않은 옵션입니다. 다시 선택해주세요.");
                    break;
            }
        }
        scanner.close();
    }
}
```

`Todo.java` 파일에 아래 내용을 붙여 넣습니다.  
```java
public class Todo {
    private String title;
    private boolean isCompleted;

    public Todo(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public String getTitle() {
        return title;
    }
}
```

`TodoListManager.java` 파일에 아래 내용을 붙여 넣습니다.  
```java
import java.util.ArrayList;
import java.util.List;

public class TodoListManager {
    private List<Todo> todos;

    public TodoListManager() {
        this.todos = new ArrayList<>();
    }

    // 새로운 할 일을 추가하는 메서드
    public void addTodo(String title) {
        Todo newTodo = new Todo(title);
        todos.add(newTodo);
        System.out.println("할 일이 추가되었습니다: " + title);
    }

    // 할 일 목록을 출력하는 메서드
    public void listTodos() {
        if (todos.isEmpty()) {
            System.out.println("할 일이 없습니다.");
            return;
        }
        System.out.println("할 일 목록:");
        for (int i = 0; i < todos.size(); i++) {
            Todo todo = todos.get(i);
            String status = todo.isCompleted() ? "[완료]" : "[미완료]";
            System.out.println((i + 1) + ". " + status + " " + todo.getTitle());
        }
    }

    // 특정 할 일을 완료 상태로 바꾸는 메서드
    public void completeTodo(int index) {
        if (index < 1 || index > todos.size()) {
            System.out.println("유효하지 않은 인덱스입니다.");
            return;
        }
        Todo todo = todos.get(index - 1);
        if (todo.isCompleted()) {
            System.out.println("이미 완료된 할 일입니다: " + todo.getTitle());
        } else {
            todo.markAsCompleted();
            System.out.println("할 일이 완료되었습니다: " + todo.getTitle());
        }
    }
}
```
<br>  


### 단계별 실습 가이드

---

#### 1단계: Ask 모드로 코드 분석하기

먼저 프로젝트를 파악하는 것부터 시작해 봅시다. **Ask 모드**를 사용해 질문하고 답변을 받아 코드를 분석하세요.

* IntelliJ에서 코파일럿 채팅창을 열고 모드가 **`Ask`**로 되어있는지 확인합니다.
* 채팅창에 `@workspace 이 프로젝트에 대해 설명해줘`를 입력하여 전체 구조에 대한 설명을 요청하세요.
    * **팁**: `@workspace`는 **채팅 참여자**로, 프로젝트 전체를 분석하도록 지시합니다. Ask 모드는 이를 바탕으로 채팅창에 답변을 제시합니다.
* `Main.java` 파일을 연 후, 채팅창에 `/explain 이 파일이 무슨 역할을 해?`를 입력해 파일의 역할을 물어보세요.
*  그 다음 채팅창에 `/doc 이 파일에 대해 설명하는 주석을 달아줘`를 입력하세요.
    * **팁**: `/explain`와 `/doc`은 **슬래시 명령어**로, 복잡한 질문 대신 간단하게 코드 설명을 요청할 때 사용합니다.

---

#### 2단계: 인라인 채팅으로 코드 이해하기

이제 코드 자체를 더 깊게 이해해 봅시다. **인라인 채팅**을 사용하면 코딩 흐름을 끊지 않고 바로바로 코드의 역할에 대해 질문하고 답변을 받을 수 있습니다.

* `Todo.java` 파일을 엽니다.
* `isCompleted()` 메서드에 커서를 둡니다.
* `마우스 우클릭` > `GitHub Copilot` > `Copilot: Open Inline Chat` 버튼을 눌러 **인라인 채팅**을 활성화하세요.
* 코드 옆에 나타나는 **채팅창**에 `/explain 이 메서드가 무슨 역할을 해?`와 같이 원하는 질문을 입력합니다.
* 코파일럿이 해당 메서드의 역할에 대해 설명해줄 것입니다.
* 다른 파일의 코드도 같은 방법으로 직접 질문하며 그 역할을 파악해 보세요.

---

#### 3단계: 스마트 액션으로 코드 개선하기

이미 완성된 코드를 더 좋게 다듬어 볼 차례입니다. **스마트 액션**을 사용하면 코드를 선택만 해도 다양한 개선 작업을 할 수 있습니다.

* `TodoListManager.java` 파일을 열고, `addTodo` 메서드를 마우스로 드래그해서 선택하세요. 
* 코드 바로 옆에 나타나는 작은 아이콘들을 확인하세요. 이것이 바로 **스마트 액션**입니다.
* `addTodo` 메서드를 드래그 후 `마우스 우클릭` > `GitHub Copilot` > `Explain This` 를 통해 설명을 생성합니다. <br>  
<img width="1011" height="823" alt="image" src="https://github.com/user-attachments/assets/ee8a7d70-3251-4ed5-884a-c8d735752f0a" />
  <br> <br>
    
* `addTodo` 메서드를 드래그 후 `마우스 우클릭` > `GitHub Copilot` > `Generate Docs`를 선택해 주석(문서)을 생성합니다. <br>
<img width="803" height="809" alt="image" src="https://github.com/user-attachments/assets/5e227c05-9dd2-4e57-aee5-29800f898a47" />
<br>  <br>  
* 이 기능들을 사용해서 다른 코드들에 대해 설명을 생성하거나 주석을 생성해보세요.  

---

#### 4단계: Agent 모드와 Edit 모드로 문서화하기

코드를 모두 이해하고 개선했으니, 이제 프로젝트를 다른 팀원들에게 공유하기 위해 문서를 작성해 봅시다.

* 채팅창의 모드를 **`Agent`**로 변경합니다.
* 아래 프롬프트를 입력해서 `README.md` 파일을 생성해달라고 요청하세요.
    * `@workspace 이 코드 저장소에 대한 README.md 파일 내용을 생성해줘. 제목은 "Java To-Do App"으로 하고, 설명, 프로젝트 구조, 주요 클래스, 사용법, 라이선스 섹션을 포함시켜줘.`
    * **설명**: `Agent` 모드는 요청을 이해하고 **직접 `README.md` 파일을 생성**해줍니다.
* 이제 방금 생성된 문서를 수정해봅시다. 채팅 모드를 **`Edit`**으로 변경하세요.
* `Add Files` 버튼을 눌러 `README.md` 파일을 선택합니다.
* 채팅창에 `#file README.md "주요 클래스" 섹션에 각 클래스의 역할에 대한 상세 설명을 추가해줘`를 입력합니다.
    * **설명**: `#file`은 **채팅 변수**로, 특정 파일을 지정해 작업을 요청할 수 있습니다. `Edit` 모드는 이 파일에만 작업을 수행합니다.


#### 5단계: 인라인 채팅과 채팅 뷰의 각 모드에서 사용 가능한 슬래쉬 변수를 비교해보세요.  
  
