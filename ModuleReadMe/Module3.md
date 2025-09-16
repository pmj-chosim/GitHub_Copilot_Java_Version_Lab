### 시나리오

당신은 팀의 신규 프로젝트를 맡게 되었습니다. 동료가 남겨둔 것은 기본적인 파일 구조와 이미 완성된 코드뿐입니다.   
당신의 임무는 GitHub Copilot의 도움을 받아 이 코드를 분석하고,  **새로운 기능('할 일 삭제')** 을 추가하며, 완벽하게 문서화하는 것입니다.  

### 실습 준비

이 실습은 앞서 모듈 2에서 사용했던 코드를 동일하게 사용합니다.  
모듈 2 실습 폴더를 삭제하지 않았더라면 새 프로젝트 생성 및 베이스 코드 생성 과정을 생략해도 무관합니다.  

모듈 2 실습 폴더가 없다면 아래 내용을 통해 베이스 코드를 생성해주세요.  
--

먼저 프로젝트를 설정해 주세요.  

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

--

  
4.  위에서 제공된 완성된 코드를 각 파일에 붙여 넣으세요.

---

### 단계별 실습 가이드

---

#### **1단계: 인라인 채팅으로 새로운 기능 개발하기**

이제 '할 일 삭제'라는 새로운 기능을 개발해 봅시다. **인라인 채팅**을 사용하면 코딩 흐름을 끊지 않고 바로바로 도움을 받을 수 있습니다.

1.  `TodoListManager.java` 파일의 마지막 메서드 아래에 새로운 메서드 시그니처인 `public void removeTodo(int index)`를 입력하세요.
2.  커서를 메서드 본문 안에 두고, **인라인 채팅** (`마우스 우클릭` > ``GitHub Copilot` > `Copilot: Open Inline Chat` 을 활성화하세요.  <br>  
<img width="617" height="357" alt="image" src="https://github.com/user-attachments/assets/2a2f0234-c35b-42df-a3f7-ac669a6fc9aa" />  <br>  

3.  채팅창에 `받아온 인덱스에 해당하는 할 일을 리스트에서 삭제하고, 삭제 성공 메시지를 출력해줘. 인덱스가 유효하지 않으면 에러 메시지를 출력해줘.`라고 요청합니다.  
4.  코파일럿이 코드를 제안해주면 `Tab` 키로 받아들여 메서드를 완성합니다.  
5.  `TodoListManager.java`에서 방금 추가한 `removeTodo` 메서드를 마우스로 드래그해서 선택하세요.  
6.  마우스 우클릭 > `GitHub Copilot` > `Open Inline Chat`을 선택한 뒤, `이 코드를 더 효율적으로 리팩터링해줘`와 같이 요청해 코드를 개선할 수 있습니다.

---

  
#### **2단계: 자동 완성으로 기능 개발하기**

이제 '할 일 목록을 파일에 저장하는 기능'을 개발해 봅시다. **인라인 채팅**을 사용해 코딩 흐름을 끊지 않고 바로바로 도움을 받을 수 있습니다.

1. `TodoListManager.java` 파일에 새로운 메서드 시그니처인 `public void saveTodosToFile(String filename)`를 입력하세요.
2. 커서를 메서드 본문 안에 둔 다음, 엔터를 누르면 GitHub Copilot이 자동으로 코드를 제시해줍니다. <br>
<img width="750" height="271" alt="image" src="https://github.com/user-attachments/assets/9e3265a7-4533-43e8-af54-3932839f77ba" />  <br>
  
3. `Tab` 버튼을 눌러 제안된 코드를 수락합니다.   

