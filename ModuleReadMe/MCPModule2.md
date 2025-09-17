### **IntelliJ에서  Atlassian Confluence(Confluence MCP) 사용 설정하기**

이 가이드는 IntelliJ IDEA의 GitHub Copilot 기능과 연동하여, Atlassian Confluence의 내용을 MCP(Model Context Protocol)를 통해 활용할 수 있도록 설정하는 방법을 안내합니다. 로컬 환경에서 Docker(또는 Podman)를 사용하여 Atlassian MCP 서버를 실행하는 것을 기준으로 합니다.

#### **0단계: 사전 준비**

본격적인 설정에 앞서, 다음 프로그램들이 설치 및 준비되어 있어야 합니다.

* **IntelliJ IDEA**: 최신 버전 설치 권장
* **GitHub Copilot 플러그인**: IntelliJ에 설치 및 로그인 완료
* **Docker 또는 Podman**: 컨테이너 실행을 위한 프로그램 (이 가이드에서는 둘 다 사용 가능)

---

### **1단계: Confluence 연결 정보 확인하기**

`mcp.json` 설정 파일에 입력할 여러분의 Confluence URL, 사용자 이름, API 토큰을 미리 찾아야 합니다.

#### **1. Confluence URL**

* 사용 중인 [Confluence 사이트](https://www.atlassian.com/ko/software/confluence)에 접속 후 로그인 했을 때 브라우저 주소창에 표시되는 **기본 URL**입니다.  
이 URL을 메모장 등에 메모해주세요.   
* **예시**: `https://your-domain.atlassian.net/wiki`  
<img width="647" height="287" alt="image" src="https://github.com/user-attachments/assets/d792ebb1-4cb5-4f9f-8b54-63e46ba70db2" />  

#### **2. Confluence 사용자 이름 (Username)**

* Confluence (Atlassian) 계정에 로그인할 때 사용하는 **본인의 이메일 주소**입니다.  
* **예시**: `your-email@example.com`  
이 메일 주소를 메모장 등에 메모해주세요.    
  
#### **3. Confluence API 토큰 (API Token)**

API 토큰은 비밀번호 대신 프로그램이 Confluence에 안전하게 접근할 수 있도록 해주는 인증 키입니다.  

1.  아래의 Atlassian API 토큰 발급 페이지로 이동하여 로그인합니다.  
    * **[https://id.atlassian.com/manage-profile/security/api-tokens](https://id.atlassian.com/manage-profile/security/api-tokens)**

2.  `API 토큰 만들기` (Create API token) 버튼을 클릭합니다.  <br>  
    <img width="976" height="472" alt="image" src="https://github.com/user-attachments/assets/12fc82af-8ee0-40d8-b038-b80bee0c8585" />  <br>  
  

3.  토큰의 용도를 알아보기 쉽도록 이름(Label)을 입력합니다. (예: `intellij-mcp-token`)  
  

4.  `만들기` (Create) 버튼을 클릭하면 API 토큰이 생성됩니다.  

    > **⚠️ 중요**: 이 토큰 값은 **화면을 벗어나면 다시 확인할 수 없으므로**, 반드시 `복사` 버튼을 눌러 다른 곳에 안전하게 즉시 저장 및 메모해두세요.
  <br>  
<img width="915" height="512" alt="image" src="https://github.com/user-attachments/assets/0473e30a-1443-4713-bd3d-c9b337ff4496" />  
  

---

### **2단계: IntelliJ에서 MCP 설정 파일 열기**

이제 IntelliJ에서 GitHub Copilot의 MCP 설정 파일을 열 차례입니다.

1.  IntelliJ IDEA 창의 우측에 있는 **GitHub Copilot 채팅**을 클릭합니다.  <br>  
<img width="168" height="346" alt="image" src="https://github.com/user-attachments/assets/1be77466-76ce-41b9-afc4-e7731fa3bb95" />  <br>  

2.  채팅의 입력하는 공간에서 채팅 모드를 `Agent`로 선택 후, 도구 모양을 클릭합니다.  <br>  
    <img width="682" height="277" alt="image" src="https://github.com/user-attachments/assets/daf02c73-6ff6-47d8-a094-930502151b36" />  <br>  

3.  설정창이 열리면 `+Add More Tools..`를 클릭합니다.  <br>  
<img width="878" height="737" alt="image" src="https://github.com/user-attachments/assets/072e3ab9-97c0-4fa9-b20a-6b8a7b8777a5" />  <br>  

4.   `mcp.json` 파일이 자동으로 열립니다.   <br>  
    <img width="785" height="563" alt="image" src="https://github.com/user-attachments/assets/05522ac0-6794-486b-8329-81c2b9562efe" />  <br>  


---

### **3단계: `mcp.json` 파일에 Atlassian 서버 정보 추가하기**

비어 있거나 기본 내용만 있는 `mcp.json` 파일에 아래의 내용을 복사하여 붙여넣습니다.
(1) Docker를 사용할 경우  
```json
{
    "servers": {
        "mcp-atlassian": {
            "command": "docker",
            "args": [
                "run",
                "-i",
                "--rm",
                "-e",
                "CONFLUENCE_URL",
                "-e",
                "CONFLUENCE_USERNAME",
                "-e",
                "CONFLUENCE_API_TOKEN",
                "ghcr.io/sooperset/mcp-atlassian:latest"
            ],

            "env": {
                "CONFLUENCE_URL": "여기에 1단계에서 찾은 URL을 입력하세요",
                "CONFLUENCE_USERNAME": "여기에 1단계에서 찾은 이메일을 입력하세요",
                "CONFLUENCE_API_TOKEN": "여기에 1단계에서 발급받은 API 토큰을 입력하세요"
            }
        }
    }
}
```

  
(2) Podman을 사용할 경우  
```json
{
    "servers": {
        "mcp-atlassian": {
            "command": "docker",
            "args": [
                "run",
                "-i",
                "--rm",
                "-e",
                "CONFLUENCE_URL",
                "-e",
                "CONFLUENCE_USERNAME",
                "-e",
                "CONFLUENCE_API_TOKEN",
                "ghcr.io/sooperset/mcp-atlassian:latest"
            ],
            "env": {
                "CONFLUENCE_URL": "여기에 1단계에서 찾은 URL을 입력하세요",
                "CONFLUENCE_USERNAME": "여기에 1단계에서 찾은 이메일을 입력하세요",
                "CONFLUENCE_API_TOKEN": "여기에 1단계에서 발급받은 API 토큰을 입력하세요"
            }
        }
    }
}
```

예시:  
<img width="1472" height="722" alt="image" src="https://github.com/user-attachments/assets/e7158b42-af12-4761-af9f-09972f1ad268" />  <br>  


입력 완료 파일 내용을 저장해주세요.  
<br>  
저장 완료 후  Atlassian Confluence이 잘 연결됐는지 확인해보겠습니다.  
좌측의 도구를 클릭하면 `GitHub Copilot MCP Log`를 확인할 수 있습니다.  <br>  
<img width="1036" height="396" alt="image" src="https://github.com/user-attachments/assets/39d48bf2-6858-46e2-9847-551d7e22cb38" />  <br>  

`Server`에서 `mcp-atlassian`을 선택하면 Atlassian MCP 연결에 대한 로그만 확인할 수 있습니다.  <br>  
<img width="808" height="107" alt="image" src="https://github.com/user-attachments/assets/da5923cf-27d0-4589-824b-1d0844805c15" />






