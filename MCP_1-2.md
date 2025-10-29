MCP가 없었을 때

도구 lanchain 버전  
```bash
import requests
from bs4 import BeautifulSoup
from langchain.llms import OpenAI
from langchain.agents import Tool # 1. Tool 클래스를 임포트

# 2. 순수 Python 함수를 먼저 정의
def get_text_from_url(url: str) -> str:
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup.get_text()

# 3. 함수(func)와 설명(description)을 Tool 객체로 '포장'
tool = Tool(
    name="Web Scraper",
    func=get_text_from_url,
    description="useful for when you need to get the text from a web page"
)
# (langchain으로 된 에이전트만 [tool] 리스트를 전달받음)
```

도구 sementic kernel 버전 
```bash
import requests
from bs4 import BeautifulSoup
import semantic_kernel as sk
from semantic_kernel.functions import kernel_function, Description # 1. 임포트
from typing import Annotated

# 2. 관련 도구(함수)들을 '클래스'로 묶음
class WebScraperPlugin:

    # 3. @kernel_function 데코레이터로 AI에게 메타데이터를 제공
    @kernel_function(
        name="get_text_from_url",
        description="useful for when you need to get the text from a web page"
    )
    def get_text_from_url(
        self, 
        url: Annotated[str, Description("The URL of the web page to scrape")]
    ) -> str:
        try:
            response = requests.get(url)
            response.raise_for_status()
            soup = BeautifulSoup(response.text, 'html.parser')
            return soup.get_text()
        except requests.RequestException as e:
            return f"Error fetching URL: {e}"
# (커널에 kernel.import_plugin_from_object(WebScraperPlugin())로 등록)
```

MCP 방식을 쓰면  이렇게 도구를 개발하면 돼요.    
```bash
import requests
from bs4 import BeautifulSoup

# 2. 함수 정의부에 @tool 데코레이터를 직접 붙임
@tool(description="useful for when you need to get the text from a web page")
def get_text_from_url(url: str) -> str:
    """주어진 URL에서 텍스트를 스크래핑합니다."""
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup.get_text()

# 3. 'get_text_from_url' 함수 자체가 이제 Tool 객체처럼 동작합니다.
# (이름은 함수명 'get_text_from_url'로, func는 함수 본체로 자동 설정됨)
# (에이전트는 [get_text_from_url] 리스트를 전달받음)
```
  
  


클라이언트(AI 서비스 예시)  
```
# --- Semantic Kernel에서 이 플러그인을 사용하는 방법 ---

# 1. 커널(Kernel) 초기화
kernel = sk.Kernel()

# (선택 사항: LangChain의 OpenAI LLM을 설정하는 것과 동일)
# from semantic_kernel.connectors.ai.open_ai import OpenAIChatCompletion
# kernel.add_service(
#     OpenAIChatCompletion(
#         service_id="default",
#         api_key="YOUR_OPENAI_API_KEY"
#     )
# )

# 2. 플러그인을 커널에 추가 (가장 중요)
# 'WebScraperPlugin'의 인스턴스를 커널에 "WebScraper"라는 이름으로 등록합니다.
kernel.import_plugin_from_object(WebScraperPlugin(), plugin_name="WebScraper")

# 3. 이제 커널(및 이 커널을 사용하는 에이전트)이 도구를 사용할 수 있습니다.
#    AI는 "WebScraper.get_text_from_url"라는 이름으로 이 함수를 인식합니다.
print("WebScraperPlugin이 커널에 성공적으로 로드되었습니다.")
print("사용 가능한 함수:")
for func in kernel.plugins["WebScraper"].functions:
    print(f"- {func}")

# 위에서 만든 'plugin'을 커널에 추가하여 에이전트가 사용할 수 있습니다.
# (예: AutoFunctionCallingAgent를 만들 때 이 kernel 객체를 전달)
```
