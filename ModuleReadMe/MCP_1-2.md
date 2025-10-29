##  "MCP가 없으면" AI와 도구를 어떻게 연결했을까요?  

AI(LLM)는 똑똑하지만 혼자서는 '복잡한 현실세계의 일'을 못합니다. 웹사이트를 검색하거나, 오늘 날씨를 알려주거나, 코드를 실행할 수 없죠. 이 심부름을 시키기 위해 AI에게 **도구**(Tool)를 쥐여줘야 합니다.  

그런데 과거에는 이 '도구'의 규격이 통일되지 않았습니다.  

MCP가 없으면 AI와 도구를 어떻게 연결했을까요?  

MCP(Model Context Protocol)라는 **표준 규격**(USB-C)이 없었을 때는, AI 에이전트(AI가 탑재된 프로그램)와 도구를 연결하기 위해 **각 AI 프레임워크가 만든** '**독자적인 규격**'에 맞춰 도구를 개발해야 했습니다.  

마치 Apple의 '라이트닝 8핀'과 예전 삼성의 '20핀'처럼, 프레임워크(AI)가 바뀌면 도구(충전기)도 전부 그 규격에 맞춰 새로 만들어야 하는 비효율적인 상황이었습니다.  
  
AI 발전 초기에도 그런 상황이었습니다.  

그 예로,  
### 1. LangChain 라이브러리 사용 방식"이 있었습니다.
가장 대표적인 LangChain 프레임워크를 사용하려면, 개발자는 'LangChain 전용' 도구와 'LangChain 전용' AI를 만들어야 했습니다.   
  
AI 에이전트와 연결하기 위해, 개발자는 순수 Python 함수(심부름 기능)를 LangChain의 Tool 클래스라는 '독자 규격(라이트닝 8핀)'으로 '포장'해야 했습니다.  

**[ 1. 도구 정의 (LangChain 'Tool' 클래스) ]**
```python
import requests
from bs4 import BeautifulSoup
from langchain.agents import Tool # 1. LangChain의 'Tool' 클래스(포장지)를 임포트

# 2. '웹 스크래핑'이라는 순수 Python 함수(기능)를 먼저 정의
def get_text_from_url(url: str) -> str:
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup.get_text()

# 3. 함수(func)와 설명(description)을 LangChain 전용 Tool 객체로 '포장'
web_scraper_tool = Tool(
    name="Web Scraper",
    func=get_text_from_url, # <- 실제 기능을 여기에 끼워넣음
    description="useful for when you need to get the text from a web page"
)
```

**[ 2. 클라이언트 예시 (LangChain 에이전트) ]** 
```python
from langchain_openai import ChatOpenAI
from langchain.agents import initialize_agent, AgentType

llm = ChatOpenAI(model="gpt-4-turbo", temperature=0)

# 1. 위에서 '포장한' Tool 객체를 리스트에 담아 전달
tools = [web_scraper_tool]

# 2. LangChain 에이전트(AI)가 이 [tools] 리스트를 '같은 프로그램 안에서' 직접 전달받아 인식함
agent = initialize_agent(tools, llm, agent=AgentType.OPENAI_FUNCTIONS)
# agent.run(...)
```
한계점: 여기서 LangChain 라이브러리를 사용해서 만든 도구 `web_scraper_tool`은 LangChain 에이전트(AI)하고만 작동합니다. 다른 프레임워크(SDK, 예: Semantic Kernel)로 개발된 에이전트(AI)는 이 도구를 전혀 알아듣지 못했습니다.


### 2. 마찬가지로, "Semantic Kernel 라이브러리"를 사용해 도구와 AI를 연결하는 방법도 있었습니다.  

AI 클라이언트가 Semantic Kernel(SK)을 쓴다면, 도구 역시 Semantic Kernel의 Plugin이라는 '독자 규격(삼성 20핀)'에 맞춰 개발해야 했습니다.     

**[ 1. 도구 정의 (Semantic Kernel 'Plugin' 클래스) ]**
```python
import requests
from bs4 import BeautifulSoup
import semantic_kernel as sk
from semantic_kernel.functions import kernel_function, Description
from typing import Annotated

# 1. 관련 도구(함수)들을 'Plugin 클래스'라는 상자로 묶음
class WebScraperPlugin:

    # 2. @kernel_function 이라는 'Sementic Kernel 전용 스티커'를 함수에 붙임
    @kernel_function(
        name="get_text_from_url",
        description="useful for when you need to get the text from a web page"
    )
    def get_text_from_url(
        self, 
        url: Annotated[str, Description("The URL of the web page to scrape")]
    ) -> str:
        # ... (스크래핑 로직) ...
        response = requests.get(url)
        soup = BeautifulSoup(response.text, 'html.parser')
        return soup.get_text()
```

**[ 2. 클라이언트 예시 (Semantic Kernel 커널) ]**  
```python
# 1. 커널(Kernel) 초기화
kernel = sk.Kernel()
# (kernel.add_service(...)로 LLM 설정)

# 2. 위에서 정의한 'Plugin 클래스'의 인스턴스를 커널에 '등록'
kernel.import_plugin_from_object(WebScraperPlugin(), plugin_name="WebScraper")

# 3. 이제 커널이 "WebScraper.get_text_from_url"라는 이름으로 도구를 인식함
# (이 커널을 사용하는 에이전트가 이 도구를 사용할 수 있음)
```

한계점: 이 `WebScraperPlugin` 도구 역시 Sementaic Kernek 라이브러리를 사용해서 개발된 에이전트(AI)에 대해서만 작동합니다.  

### 3. "MCP 방식"의 등장: `@mcp.tool`의 의미  
이런 '호환성 지옥'을 해결하기 위해 **우리** '**USB-C**'처럼 **표준 규격**을 만들자!라고 제안된 것이 바로 **MCP(Model Context Protocol)**입니다.  
  
MCP는 AI와 도구가 서로 '네트워크(HTTP)'를 통해 대화하는 **표준 API 규약**입니다.  
  
(1) "표준 API 규약"이란?   
이전의 LangChain, Semantic Kernel 방식은 "**같은 SDK(소프트웨어 개발 키트)**"를 사용해야 했습니다.  AI 클라이언트와 도구(Tool)가 langchain이라는 '같은 규격'을 쓰며 통신했습니다.  (LangChain 사용 도구는 LangChain 사용 AI 에이전트랑만 소통 가능)  
  
MCP는 이 접근 방식을 완전히 바꿨습니다. "AI와 도구가 서로 다른 언어(Java/Python)로, 서로 다른 컴퓨터에서 실행되어도, **표준화된 HTTP API**로만 통신하자"는 규칙입니다.
  
이 '표준 API'는 AI가 도구(서버)에게 "심부름"을 시키기 위해 **두 개의 정해진 URL(엔드포인트**만 사용하도록 약속합니다.  
비유하자면, MCP라는 'USB-C 포트'에는 수많은 핀이 있지만, AI와 도구가 대화할 땐 딱 2개의 핵심 핀(규칙)만 쓰자고 약속한 겁니다.  

1. `GET /discover (탐색)` (1번 핀):
- AI가 도구(충전기)를 포트에 꽂자마자 "너 어떤 기능(도구)들 가지고 있어?  사용법(설명, 인수)이 담긴 '메뉴판(JSON)' 좀 보내줘."

> JSON(메뉴판) 예시: AI는 도구 서버의 /discover 핀(엔드포인트)에서 다음과 같은 '표준 메뉴판(url)'을 받습니다.
> ```JSON
> {
>  "name": "WebScraperServer",
>  "tools": [
>    {
>      "name": "get_text_from_url",
>      "description": "useful for when you need to get the text from a web page",
>      "parameters": {
>        "type": "object",
>        "properties": {
>          "url": {
>            "type": "string",
>            "description": "The URL of the web page to scrape"
>          }
>        },
>        "required": ["url"]
>      }
>    }
>    // (만약 다른 도구(함수)가 더 있다면 여기에 계속 추가됩니다)
>  ]
>}
>```

2. `POST /execute (실행)`(2번 핀):
- AI가 도구 서버에게: "메뉴판(`/discover`)을 보니 '웹 스크래핑' 도구가 있네. 사용자가 요청한 이 주소(url)로 가서 텍스트 좀 긁어다 줘." (JSON으로 '주문서' 전송)
- `POST /execute`로 전송되는 '주문서(JSON)' 예시: 
AI가 "https://www.google.com"의 텍스트를 긁어오기로 결정했다면, `/execute` 핀(엔드포인트)에 다음과 같은 '표준 주문서'를 보냅니다.
> ```JSON
> {
>  "tool": "get_text_from_url",
>  "arguments": {
>    "url": "https://google.com"
>  }
>  ```  

- 도구 서버는 이 '주문서'를 받고, `get_text_from_url(url="https://google.com")`  함수를 실행합니다.  이 함수는 https://google.com 의 HTML 코드 중에서 태그를 제외한 '순수한 글자(텍스트)'만 긁어옵니다. 그리고 그 결과(긁어온 텍스트)를 AI에게 다시 돌려줍니다.  


(2) "1:1 대응"의 진짜 의미: '네트워킹 자동화'
이제 "Java의 `@Tool`과 Python의 `@mcp.tool`이 어떻게 1:1 대응이라는 건가요?"라는 질문에 답할 수 있습니다.

**문제점**: 위에서 설명한 /discover, /execute API를 제공하는 웹 서버를 직접 만드는 것은 매우 복잡하고 귀찮은 일입니다. 
이건 마치 개발자(도구 제작자)에게 "USB-C 포트 규격서 줄 테니, 네가 직접 핀마다 납땜하고 배선해서 포트(서버)를 만들어!"라고 요구하는 것과 같습니다. (Spring Boot / FastAPI로 이 '핀 배선'을 수동으로 구현해야 함)  

**해결책 (1:1 대응)**: **Java의 @Tool** (Spring AI)과 **Python의 @mcp.tool** (FastMCP 라이브러리)은 이 **'납땜과 배선 과정**(HTTP API 서버 구현)'을 '자동화'해주는 마법 같은 기능입니다.  
  
개발자는 그냥 '웹 스크래핑'이라는 '완제품 기능(순수 함수)'만 만듭니다. 그 위에 **@mcp.tool**이라는 **KC 인증(표준) 스티커**를 딱 붙이면, 프레임워크가 알아서 완벽한 'USB-C 포트(API 서버)'를 뚝딱 만들어서 달아주는 셈입니다.  
  

이것이 "개발자 경험" 관점에서 두 방식이 1:1로 완벽하게 대응된다는 의미입니다.   

(3) **[ 1. 도구 정의 (MCP 표준 방식, FastMCP 사용-@mcp.tool) ]**
```
import requests
from bs4 import BeautifulSoup
from mcp.server.fastmcp import FastMCP # 1. FastMCP 라이브러리를 임포트

# 2. FastMCP 서버 인스턴스 생성
mcp = FastMCP(name="WebScraperServer")

# 3. @mcp.tool 데코레이터를 함수에 바로 붙임
# (이 함수를 MCP 표준 API로 '노출'하겠다는 의미)
@mcp.tool
def get_text_from_url(url: str) -> str:
    # 4. AI는 이 docstring을 자동으로 읽어서 'description'으로 사용합니다.
    """useful for when you need to get the text from a web page"""
    
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup.get_text()

# 5. 서버 실행 (이 명령어가 /discover, /execute 서버를 자동으로 띄움)
if __name__ == "__main__":
    mcp.run()
```  

**[ 2. 클라이언트 예시 (MCP 호환 에이전트) ]**
```python
from azure.ai.agent import AzureAIAgent # (MCP 호환 클라이언트 예시)

# 1. LangChain/SK처럼 'SDK 객체'를 전달하는 것이 아니라,
#    도구 서버의 '네트워크 주소(URL)'를 알려줍니다.
mcp_tool_server_url = "http://localhost:8000" # (FastMCP가 띄운 서버 주소)

# 2. AI 에이전트가 이 MCP 서버 주소를 등록합니다.
agent = AzureAIAgent(
    llm=...,
    tool_servers=[mcp_tool_server_url] # MCP 표준 포트(USB-C)에 연결
)

# 3. 에이전트가 실행되면, 서버의 /discover를 호출해 도구를 '동적'으로 발견
# agent.run("https://google.com 내용 요약해줘")
```

(4) 아키텍처는 어떻게 바뀐 건가요? (`SDK-SDK` vs `SDK-[MCP]-Server`)  
여기서 "클라이언트도 `AzureAIAgent`라는 SDK를 쓰는데, 그럼 LangChain SDK 쓰는 거랑 뭐가 다른가요?"라는 날카로운 질문이 나올 수 있습니다.  
  
SDK의 '역할'이 완전히 달라졌습니다.  

1. 과거 (독자 규격): `SDK - SDK` 구조  
- AI 에이전트(Agent)와 도구(Tool)가 모두 같은 `SDK`에 종속되어 '하나의 프로그램' 안에서 강하게 결합된 구조였습니다. (예: `LangChain Agent`는 `LangChain Tool`만 알아들음)

2. 현재 (MCP 표준): `SDK - [MCP] - Server` 구조  

AI 에이전트와 도구가 **네트워크**로 완벽히 분리된 구조입니다.

- `SDK`(클라이언트, 예: AzureAIAgent): AI 에이전트입니다. 이 SDK는 '라이트닝 규격'을 강제하는 게 아니라, **USB-C(MCP)** 표준'으로 "말하는 법"만 알고 있습니다.   
- `[MCP]` (링크): 둘을 연결하는 '표준(USB-C)'이자 '네트워크(HTTP)'입니다.
- `Server` (도구, 예: @mcp.tool 서버): SDK에서 **완전히** **독립**한 '도구 프로그램'입니다. `@mcp.tool` 덕분에 도구가 SDK의 '부품'이 아닌, 고유한 주소(URL)를 가진 '완제품 서버'가 된 것입니다.

           

(5) MCP 규격으로 도구를 정의하면 무엇이 좋을까요?  
이렇게 MCP라는 '표준(USB-C)'에 맞춰 도구를 개발하면, LangChain이나 SK 같은 '독자 규격'을 쓸 때와는 비교할 수 없는 엄청난 장점들이 생깁니다.  

1. 완벽한 분리 (언어/기술 독립성): AI 클라이언트와 도구 서버가 완전히 분리됩니다. AI는 Python으로, **도구는 Java**(Spring AI)로 만들어도, 둘은 **표준 MCP**(HTTP)라는 공통 언어로 완벽하게 통신할 수 있습니다.

2. 엄청난 재사용성 (호환성): get_text_from_url 도구를 MCP 표준으로 단 한 번만 잘 만들어두면, LangChain이든 Semantic Kernel이든, 미래에 어떤 새로운 AI 프레임워크가 나오든 **MCP 표준(USB-C)만 지원하면 모두가 이 도구를 가져다 쓸 수 있습니다.** '호환성 지옥'이 끝나는 것입니다.

3. 동적 발견 (유연성): AI가 실행 중인 상태에서도, 새로운 MCP 서버 주소(URL)만 알려주면 AI가 실시간으로 새 도구를 '발견(`discover`)'하고 사용할 수 있습니다. 프로그램을 껐다 켤 필요가 없습니다.
