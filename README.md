# GitHubCopilot_Develop_Java  

## 1. IntelliJ에 GitHub Copilot 설치하기  

(1) IntelliJ에서 `File` > `Settings` > `Plugins` > 검색창에 `GitHub Copilot` 검색 > `Install`(설치 버튼)을 눌러 설치   
(2) IntelliJ 맨 우측에 있는 GitHub Copilot 버튼을 눌러 채팅을 연 후 로그인 버튼을 눌러 로그인  
  
<img width="578" height="473" alt="image" src="https://github.com/user-attachments/assets/481618b0-04b5-42f6-a13f-efedc3c37a82" />  

<img width="872" height="353" alt="image" src="https://github.com/user-attachments/assets/d1857c86-aed8-4186-a6d8-50e62497f587" />  

<img width="531" height="354" alt="image" src="https://github.com/user-attachments/assets/8fab3af9-cbc9-4dda-a1ff-bb9b5cf742a5" />  


## 2. GitHub Copilot의 기능들을 살펴 봅니다.  
- 코드 완성 기능
- 채팅

이 두 가지 기능을 각각 실습을 통해 살펴 봅니다.  
실습 전, 실습을 할 수 있도록 새로운 프로젝트를 먼저 열어 봅니다.  

IntelliJ에서 `File` >  `New` > `Project` 를 통해 프로젝트를 만듭니다.  
프로젝트명과 Java 버전은 상관없으나, 아래와 같이 `module1` 이름과 `17` 버전을 사용하길 권장드립니다.  
<img width="976" height="523" alt="image" src="https://github.com/user-attachments/assets/1b711fa1-e1da-467c-be79-571425786704" />  
  

### (1) 코드 완성 기능  
코드 완성 기능은 사용자가 코드를 작업하는 공간에서 코드를 제안 받는 것입니다.  
주석을 작성하거나 자동 작성을 통해 코드를 완성할 수 있습니다.  
<br>  

#### - 주석을 통해 원하는 기능을 자동으로 제안 받을 수 있습니다.
<img width="912" height="636" alt="image" src="https://github.com/user-attachments/assets/cfe369dc-95da-4e72-86bf-83266c65fc5b" />

앞서 생성한 프로젝트의 `Main.java` 파일에 위 사진과 같이 주석을 작성합니다.  
```java
// 출력 완료 메세지를 보여줍니다.
```

주석을 작성한 후 `Enter`를 눌러 다음 줄로 이동합니다.  
<img width="396" height="147" alt="image" src="https://github.com/user-attachments/assets/1a85a05d-c8d9-486f-b1ed-3a57010978da" />  
Copilot이 위 예시와 비슷하게 제안해준 내용을 수락합니다. 키보드의 `Tab` 버튼을 눌러 수락할 수 있습니다.  
<img width="322" height="86" alt="image" src="https://github.com/user-attachments/assets/c4baefcd-9dfc-4af2-8608-18094de94257" />  

<br> <br>  

#### - 코드를 작성하는 와중에도 코드를 제안 받을 수 있습니다.  
  
```java
Integer sumValueOneToTen = 0;
```
위 코드를 앞서 실습했던 내용 뒤에 붙여 넣어 주세요.  
<img width="762" height="375" alt="image" src="https://github.com/user-attachments/assets/edce3d78-e88a-4c75-90aa-366bd74f4765" />  
<br>
위 사진처럼 붙여 넣고 나서, 키보드의 `Enter` 버튼을 누릅니다.  <br>  
  
<img width="370" height="135" alt="image" src="https://github.com/user-attachments/assets/87bb0389-197c-4a34-a9da-f026550d24c6" />  
  
`Enter` 버튼을 누르면 위 사진과 같이 Copilot에게 코드를 제안 받을 수 있습니다.  
`Tab` 버튼을 눌러 Copilot의 제안을 수락할 수 있습니다.  <br>  
<img width="327" height="127" alt="image" src="https://github.com/user-attachments/assets/83f2bf26-a437-49bd-94cf-f3959bdd60a0" />  

<br>
GitHub Copilot의 코드 완성 기능을 살펴 보았습니다.  
원하는 기능을 주석을 통해 적거나, 코드를 작성하고 있는 중이라면 GitHub Copulot으로부터 코드를 제안 받을 수 있습니다.   


<br> <br>

### (2) 채팅 기능  

채팅은 다음 4가지 방법을 통해 사용할 수 있습니다. 
- 채팅 뷰
- 인라인 채팅
- 스마트 액션
- 퀵 채팅

위 방법들을 순차적으로 실습을 통해 살펴 보겠습니다.  
  
#### - 채팅 뷰  

<img width="703" height="997" alt="image" src="https://github.com/user-attachments/assets/61b7eac0-b4ba-475a-8909-f8a15c0aa29c" />  
  <br>
IntelliJ에서 우측의 GitHub Copilot 버튼을 눌러 채팅을 엽니다.   
채팅 뷰에서 모드를 `Ask`로 선택합니다.      
```java
계산기 기능을 가진 java 어플리케이션을 만들어 주세요
```
라고 채팅창에 입력합니다.  <br>
<img width="593" height="643" alt="image" src="https://github.com/user-attachments/assets/229b219a-51d8-42fe-8d87-dbb47c67f24c" />  <br>
채팅 창에 코드를 제시하며 설명을 해줄 것입니다.  

`Agent` 모드로 바꾼 다음 동일한 질문을 해보겠습니다.  <br>
<img width="622" height="861" alt="image" src="https://github.com/user-attachments/assets/c7c33c4b-2a3f-4301-a87a-7203de5745cb" />  <br>
`Agent` 모드로 바꾼 후 
```java  
계산기 기능을 가진 java 어플리케이션을 만들어 주세요
```
사진처럼 다시 동일한 질문을 같이 해보겠습니다.
<br>  
`Ask`모드는 채팅창에서 코드나 답을 제시했었습니다.  
이와 달리 `Agent` 모드에선  
Agent 모드는 "계산기 앱 만들어 줘"라고 하면 Main.java 파일에 계산기 코드를 넣어 줍니다.  
그냥 구경하는 게 아니라 직접 일을 해주는 `Agent`처럼 동작합니다.  
<img width="1527" height="907" alt="image" src="https://github.com/user-attachments/assets/d30661c3-23f0-4fc5-a26a-dab61f1cef0b" />  <br>

지금과 같이 `Main.java` 하나만 있는 간단한 프로젝트는 물론이고, 파일이 여러 개 섞여서 머리 아픈 복잡한 프로젝트도 문제없이 수정해줍니다.  
Agent 모드는 여러 내용들을 쫙 훑어보고 어디를 어떻게 고쳐야 할지 스스로 판단해서 여러 파일을 넘나들며 수정해주기 때문입니다.  

Edit 모드도 사용해보겠습니다.  
<img width="1176" height="902" alt="image" src="https://github.com/user-attachments/assets/378c4779-d905-44f8-abc7-d90012913531" />  <br>
채팅에서 `Edit`을 선택해 모드를 변경하려고 하면, 위 사진과 같은 팝업이 뜰 것입니다.  
`Discard` 버튼을 누릅니다.  
<img width="601" height="311" alt="image" src="https://github.com/user-attachments/assets/1b9bf47c-97eb-4b36-a33f-23f02ddeac64" />  <br>
`Add Files` 버튼을 누른 후, `Main.java` 파일을 선택합니다.  
<img width="651" height="381" alt="image" src="https://github.com/user-attachments/assets/525b9c2c-ff78-4efd-887f-c7dbffb6eb8f" />  <br>
`Main.java`파일을 더블 클릭하면 위와 같이 파일을 추가할 수 있습니다.  
그 다음 채팅창에 
```java
계산기 기능을 가진 java 어플리케이션을 만들어 주세요
```
이 질문을 다시 붙여 넣고 물어 보겠습니다.  

<img width="1473" height="907" alt="image" src="https://github.com/user-attachments/assets/f0976b8b-593b-4c81-9191-c6aa533b24ec" />  <br>
사진처럼 아까 선택했던 `Main.java`의 코드들을 수정해 줄 것입니다.  <br>

`Edit`에서는 선택한 Main.java 코드에서 필요한 부분만 수정했을 것입니다.  
반면, `Agent`도 작업 공간의 코드를 변경했습니다. 다만, 사용자가 따로 파일을 지정하지 않아도 스스로 필요한 파일을 찾아 여러 파일을 넘나들며 수정한다는 점에서 차이가 있습니다.  

<br>

`Ask` 모드는 채팅창에서 코드나 답을 제시하는 역할을 합니다.   
`Agent` 모드는 여러 코드들을 이해하고 코드를 직접 수정해줍니다. 더불어, 코드 작성뿐만 아니라 Java 버전 설치 등과 같은 환경 설정까지, 다양한 작업을 처리해주는 실제 대행자처럼 동작합니다.  
그리고 `Edit` 모드는 사용자가 지정한 파일에 대해서 또는 `Agent` 보다는 좁은 맥락에서 코드를 수정한다는 차이점이 있습니다.  
















  












