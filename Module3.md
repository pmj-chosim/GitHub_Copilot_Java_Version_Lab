### 시나리오

당신은 팀의 신규 프로젝트를 맡게 되었습니다. 동료가 남겨둔 것은 기본적인 파일 구조와 이미 완성된 코드뿐입니다.   
당신의 임무는 GitHub Copilot의 도움을 받아 이 코드를 분석하고,  **새로운 기능('할 일 삭제')** 을 추가하며, 완벽하게 문서화하는 것입니다.  

### 실습 준비

이 실습은 IntelliJ에서 진행됩니다. 먼저 프로젝트를 설정해 주세요.

1.  IntelliJ에서 **`File`** >  **`New`**  >  **`Project`** 를 선택해 새 프로젝트를 만듭니다.
2.  `module3`과 같은 이름으로 프로젝트를 생성하되, 샘플 코드는 만들지 않습니다.
3.  `src` 폴더에 `Main.java`, `Todo.java`, `TodoListManager.java` 세 클래스 파일을 만듭니다.
     
**`Main.java`**
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
  
**`Todo.java`**  
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
**`TodoListManager.java`**  
```java
import java.util.ArrayList;
import java.util.List;

public class TodoListManager {
    private List<Todo> todos;

    public TodoListManager() {
        this.todos = new ArrayList<>();
    }

    public void addTodo(String title) {
        todos.add(new Todo(title));
        System.out.println("새로운 할 일이 추가되었습니다: " + title);
    }

    public void listTodos() {
        if (todos.isEmpty()) {
            System.out.println("할 일이 없습니다.");
            return;
        }
        System.out.println("--- 할 일 목록 ---");
        for (int i = 0; i < todos.size(); i++) {
            Todo todo = todos.get(i);
            String status = todo.isCompleted() ? "[완료]" : "[미완료]";
            System.out.println(String.format("%d. %s %s", i + 1, status, todo.getTitle()));
        }
        System.out.println("-----------------");
    }

    public void completeTodo(int index) {
        if (index > 0 && index <= todos.size()) {
            todos.get(index - 1).markAsCompleted();
            System.out.println("할 일이 완료되었습니다.");
        } else {
            System.out.println("유효하지 않은 번호입니다.");
        }
    }
}
```


  
4.  위에서 제공된 완성된 코드를 각 파일에 붙여 넣으세요.

---

### 단계별 실습 가이드

---

#### 1단계: Ask 모드로 코드 분석하기

먼저 프로젝트를 파악하는 것부터 시작해 봅시다. **Ask 모드**를 사용해 질문하고 답변을 받아 코드를 분석하세요.

* IntelliJ에서 코파일럿 채팅창을 열고 모드가 **`Ask`** 로 되어있는지 확인합니다.
* 채팅창에 `@workspace 이 프로젝트에 대해 설명해줘`를 입력하여 전체 구조에 대한 설명을 요청하세요.
    * **팁**: `@workspace`는 **채팅 참여자**로, 프로젝트 전체를 분석하도록 지시합니다. Ask 모드는 이를 바탕으로 채팅창에 답변을 제시합니다.
* `Main.java` 파일을 연 후, 채팅창에 `/explain 이 파일이 무슨 역할을 해?`를 입력해 파일의 역할을 물어보세요.
    * **팁**: `/explain`은 **슬래시 명령어**로, 복잡한 질문 대신 간단하게 코드 설명을 요청할 때 사용합니다.

---

#### 2단계: 인라인 채팅으로 새로운 기능 개발하기

이제 '할 일 삭제'라는 새로운 기능을 개발해 봅시다. **인라인 채팅**을 사용하면 코딩 흐름을 끊지 않고 바로바로 도움을 받을 수 있습니다.

1.  `TodoListManager.java` 파일의 마지막 메서드 아래에 새로운 메서드를 추가할 공간을 만드세요.
2.  `public void removeTodo(int index)`라고 메서드 시그니처를 입력합니다.
3.  커서를 메서드 본문 안에 두고, **인라인 채팅**(`Ctrl + I` 또는 `Cmd + I`)을 활성화하세요.
4.  채팅창에 `받아온 인덱스에 해당하는 할 일을 리스트에서 삭제하고, 삭제 성공 메시지를 출력해줘. 인덱스가 유효하지 않으면 에러 메시지를 출력해줘.`라고 요청합니다.
5.  코파일럿이 코드를 제안해주면 `Tab` 키로 받아들여 메서드를 완성합니다.

---

#### 3단계: 스마트 액션으로 코드 개선하기

새로운 코드를 더 좋게 다듬어 볼 차례입니다. **스마트 액션**을 사용하면 코드를 선택만 해도 다양한 개선 작업을 할 수 있습니다.

1.  `TodoListManager.java`에서 방금 추가한 `removeTodo` 메서드를 마우스로 드래그해서 선택하세요.
2.  코드 바로 옆에 나타나는 작은 아이콘들을 확인하세요. 이것이 바로 **스마트 액션**입니다.
3.  이 기능들을 사용해서 코드를 리팩토링하거나 테스트 코드를 생성해보세요.

---

#### 4단계: Agent 모드와 Edit 모드로 문서화하기

코드를 모두 완성했으니, 이제 프로젝트를 다른 팀원들에게 공유하기 위해 문서를 작성해 봅시다.

* 채팅창의 모드를 **`Agent`**로 변경합니다.
* 아래 프롬프트를 입력해서 `README.md` 파일을 생성해달라고 요청하세요.
    * `@workspace 이 코드 저장소에 대한 README.md 파일 내용을 생성해줘. 제목은 "Java To-Do App"으로 하고, 설명, 프로젝트 구조, 주요 클래스, 사용법, 라이선스 섹션을 포함시켜줘.`
    * **설명**: `Agent` 모드는 요청을 이해하고 **직접 `README.md` 파일을 생성**해줍니다.
* 이제 방금 생성된 문서를 수정해봅시다. 채팅 모드를 **`Edit`** 으로 변경하세요.
* `Add Files` 버튼을 눌러 `README.md` 파일을 선택합니다.
* 채팅창에 `#file README.md "주요 클래스" 섹션에 각 클래스의 역할에 대한 상세 설명을 추가해줘`를 입력합니다.
    * **설명**: `#file`은 **채팅 변수**로, 특정 파일을 지정해 작업을 요청할 수 있습니다. `Edit` 모드는 이 파일에만 작업을 수행합니다.
