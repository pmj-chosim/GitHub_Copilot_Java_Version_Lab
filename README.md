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

위 사진처럼 붙여 넣고 나서, 키보드의 `Enter` 버튼을 누릅니다.  
  
<img width="370" height="135" alt="image" src="https://github.com/user-attachments/assets/87bb0389-197c-4a34-a9da-f026550d24c6" />  
  
`Enter` 버튼을 누르면 위 사진과 같이 Copilot에게 코드를 제안 받을 수 있습니다.  
`Tab` 버튼을 눌러 Copilot의 제안을 수락할 수 있습니다.  
<img width="327" height="127" alt="image" src="https://github.com/user-attachments/assets/83f2bf26-a437-49bd-94cf-f3959bdd60a0" />  










