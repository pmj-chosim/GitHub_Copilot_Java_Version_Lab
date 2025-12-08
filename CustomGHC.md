# 6. GitHub Copilot 나만의 맞춤 설정 (Customization)
이 모듈에서는 GitHub Copilot이 내 코딩 스타일과 프로젝트 규칙을 완벽하게 이해하고 따르도록 설정하는 방법을 배웁니다.  
매번 "한글로 해줘", "Java 17 써줘"라고 말할 필요 없이, 딱 한 번의 설정으로 Copilot을 나만의 전용 AI 파트너로 만들 수 있습니다.   

## 0. 설정 메뉴 진입하기
먼저 실습을 위해 설정 화면으로 이동합니다.  
  
1. IntelliJ 상단 메뉴: `File` > `Settings` (Mac은 IntelliJ IDEA > Settings)를 클릭합니다.  
2. 좌측 메뉴 이동: `Tools` > `GitHub Copilot` > `Customizations`를 선택합니다.  

 <img width="1886" height="1089" alt="image" src="https://github.com/user-attachments/assets/3b849bb3-ab28-4c85-88c8-efd03ffbae99" />  
  
  
이제 우측에 보이는 5가지 메뉴를 하나씩 실습해 봅니다.    

---

### 1. Copilot Instructions: 전반적인 규칙 설정
Copilot이 대답할 때 항상 지켜야 할 **기본 원칙**(헌법)을 설정합니다.  

1) Copilot Instructions 항목 아래의 [workspace] 버튼을 클릭합니다.

  <img width="1196" height="577" alt="image" src="https://github.com/user-attachments/assets/8c1b1e2e-ba06-4d4f-a518-dfc0b8343dc8" />

2) 입력창에 아래 내용을 복사해서 붙여넣고 OK 또는 Apply를 누릅니다.

```bash
1. 답변 언어: 기술적인 용어를 제외하고는 항상 '한국어'로 답변해 주세요.
2. 코드 스타일: 가독성을 중요하게 생각합니다. 복잡한 로직에는 반드시 한글 주석을 달아주세요.
3. 기술 스택: Java는 17 버전, Spring Boot는 3.0 이상을 기준으로 코드를 생성해 주세요.
4. 테스트: 단위 테스트는 JUnit 5와 Mockito를 사용해 주세요.
5. 메서드 명명 규칙: 카멜 케이스(snakeCase)를 사용해 주세요.
```

* 실습 확인
    
채팅창을 열고 "이 프로젝트의 메서드 명명 규칙 알려줘"라고 간단히 물어보세요.  
Copilot Instructions의 내용의 `메서드 명명 규칙` 내용을 카멜 케이스에서 스네이크 케이스로 바꿔 주세요  
  
<img width="1382" height="495" alt="image" src="https://github.com/user-attachments/assets/5989a735-6304-4d67-be36-c6f00447e562" />    


```bash
5. 메서드 명명 규칙: 스네이크 케이스를 사용해 주세요.
```
저장 후,
채팅창을 열고 다시 "이 프로젝트의 메서드 명명 규칙 알려줘"라고 간단히 물어보세요.  

---

### 2. Instruction Files: 특정 파일 전용 규칙
모든 파일이 아니라, **특정 확장자**(.md 등)나 폴더에서만 적용되는 **세부 규칙**(매뉴얼)을 만듭니다. 이번에는 README.md 파일을 작성할 때만 적용되는 규칙을 만들어봅시다.  
  
Instruction Files 항목 아래의 [workspace] 버튼을 클릭합니다.  

입력창에 아래 내용을(상단의 --- 포함) 그대로 복사해서 붙여넣고 저장합니다.  

```bash
---
applyTo: '**/*.md'
description: 'Readme 작성 규칙'
---

# Readme 작성 규칙

이 문서는 프로젝트의 Readme 파일을 작성할 때 따라야 할 규칙과 지침을 제공합니다. Readme 파일은 프로젝트에 대한 중요한 정보를 제공하며, 사용자와 기여자 모두에게 유용해야 합니다.

## 1. 기본 구조
Readme 파일은 다음과 같은 기본 구조를 따라야 합니다:
- 프로젝트 제목
- 간단한 설명
- 설치 방법
- 사용법
- 기여 방법
- 라이선스 정보
- 연락처 정보 (선택 사항)

## 2. 인사를 포함해야 합니다.
Readme 파일의 시작 부분에 아래의 간단한 인사를 포함하여 사용자에게 환영의 메시지를 전달합니다. 
"환영합니다! 이 프로젝트에 관심을 가져주셔서 감사합니다."
```

* 실습 확인
   
프로젝트 루트에 빈 README.md 파일을 하나 생성합니다. (이미 있다면 내용을 지우세요)  
채팅창에 "이 프로젝트의 Readme 내용을 작성해줘"라고 요청해 보세요.  
Copilot이 생성한 초안에 "환영합니다!" 인사말과 지정된 목차 구조가 포함되어 있는지 확인합니다.  

---

### 3. Git Commit Instructions: 커밋 메시지 자동화
Git 커밋 메시지를 생성할 때 따를 규칙을 정합니다.  
Git Commit Instructions 항목 아래의 [workspace] 버튼을 클릭합니다.  
아래 내용을 붙여넣고 저장합니다.  

```bash
커밋 메시지는 항상 한글로 작성해줘
형식은 [Feat] 기능명 으로 해줘
```

<img width="461" height="975" alt="image" src="https://github.com/user-attachments/assets/3c0945b4-7a4e-455f-9172-21225cc27d96" />    
  
* 실습 확인
아무 파일이나 간단하게 수정합니다 (예: 주석 추가).  
IntelliJ 좌측의 Commit 탭(또는 Git 창)을 엽니다.  
커밋 메시지 입력창 위의 Copilot 아이콘을 클릭합니다.  
생성된 메시지가 [Feat] ... 형식의 한글로 나오는지 확인합니다.  

<img width="520" height="786" alt="image" src="https://github.com/user-attachments/assets/282f5043-113b-4817-be3a-4c139de28d65" />  

----

### 4. Prompt Files: 재사용 가능한 질문 템플릿  
자주 사용하는 질문 양식을 저장해두고, 필요할 때마다 불러와서 사용합니다.  
    
Prompt Files 항목 아래의 [workspace] 버튼을 클릭합니다.    
  
<img width="1007" height="676" alt="image" src="https://github.com/user-attachments/assets/683ab75f-d09c-40ed-8449-ee151371da2f" />  

.github/prompts 클릭 후 +를 클릭한 후, Prompt 파일 이름을 지정합니다.
`kind-helper`라고 지정해 보겠습니다.  
<img width="593" height="241" alt="image" src="https://github.com/user-attachments/assets/fc41c864-efb7-4aec-b899-21cb4b82dc93" />

  
아래 내용을 붙여넣고 저장합니다.  
<img width="1342" height="832" alt="image" src="https://github.com/user-attachments/assets/61bdf988-a686-4ddb-b55e-76239dece957" />  


```bash
---
name: 'kind-helper'
description: '친절한 도우미 모드로 답변을 받습니다.'
---
system:
당신은 세상에서 가장 친절하고 상냥한 AI 도우미입니다.

사용자의 질문에 답변할 때는 **반드시** 아래 문장으로 시작해야 합니다:
"안녕하세요 저는 친절한 도우미입니다."

그 후, 질문에 대해 친절하고 구체적으로 설명해 주세요.
```


* 실습 확인

Copilot 채팅창 입력 부분에 있는  /를 입력해 봅니다.  
목록에서 kind-helper를 선택합니다.

아무 질문이나 입력하면 **"안녕하세요 저는 친절한 도우미입니다."**라고 인사를 시작하는지 확인합니다.  

---

#### 5. Chat Agents: 나만의 AI 페르소나 만들기
특정 역할(Role)을 가진 전문가 모드를 만듭니다. 이번에는 코드를 냉철하게 비판하는 **독설가 리뷰어**를 만들어봅시다.  

File > Settings > Tools > GitHub Copilot > Customizations을 열고,
  
Chat Agents 항목 아래의 [Workspace] 버튼을 클릭합니다.  

이름을 `Reviewer`라고 지정합니다.  

<img width="775" height="411" alt="image" src="https://github.com/user-attachments/assets/cf29d75e-9388-460d-b06d-7776e691219b" />

  
아래 내용을 붙여넣고 저장합니다.  

```md
---
description: '가차 없는 코드 리뷰 전문가'
tools: [code-analyzer]
---
당신은 20년 경력의 까칠한 시니어 개발자입니다.

1. **목표**: 사용자가 제공한 코드의 '잠재적 버그', '성능 문제', '가독성 문제'를 찾아내세요.
2. **말투**: 절대 친절하게 인사하지 마세요. 바로 문제점부터 지적하세요. ("이 코드는 쓰레기입니다" 같은 표현은 피하되, 냉소적으로 말하세요.)
3. **형식**:
    - [문제점]: 무엇이 문제인지 한 줄 요약
    - [이유]: 왜 안 좋은지 설명
    - [수정안]: 개선된 코드 블록
4. **제약**: 코드가 완벽하다면 "나쁘지 않군." 한마디만 하세요.
```

* 실습 확인

채팅창에서 
  
일부러 비효율적인 코드(예: 이중 for문)를 보여주며 "이거 어때?"라고 물어보세요.  
  
Copilot이 냉소적인 말투로 문제점을 조목조목 지적하는지 확인합니다.


