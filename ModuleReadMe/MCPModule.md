### **Java와 Spring Boot로 MCP 서버 구축하기 (로컬 개발 가이드)**

로컬 PC에서 직접 MCP(Model Context Protocol) 서버를 실행하며 개발하고 테스트하는 방법을 알아봅니다. 이 가이드에서는 Java와 Spring Boot를 사용해 간단한 계산기 서버를 만들어 보겠습니다.

전체 과정은 다음과 같습니다.
1.  **프로젝트 생성**: Spring Boot 프로젝트를 설정합니다.
2.  **핵심 코드 작성**: 서버의 기본 동작과 도구(Tool)를 구현합니다.
3.  **서버 실행 및 테스트**: 서버를 실행하고, Inspector 도구로 정상 동작하는지 확인합니다.

---
### **0단계: Java 21 설치하기** 
만약 컴퓨터에 Java 21이 설치되어 있지 않다면, 아래의 방법으로 설치를 진행해 주세요.

1.  **Project Structure 메뉴 열기**
    IntelliJ 상단 메뉴에서 `File` > `Project Structure...` 로 이동합니다.

2.  **SDK 설정 화면으로 이동**
    왼쪽 메뉴에서 `Platform Settings` > `SDKs`를 선택합니다. 이곳에서 현재 IntelliJ에 설정된 JDK 목록을 볼 수 있습니다.

3.  **새로운 JDK 다운로드**
    상단의 `+` 아이콘 (Add New SDK)을 클릭한 뒤, 드롭다운 메뉴에서 `Download JDK...`를 선택합니다.

4.  **JDK 버전 및 공급업체 선택**
    `Download JDK` 팝업창이 나타나면 다음과 같이 설정합니다.
    * **Version**: `21` 을 선택합니다. (LTS 버전인지 확인)
    * **Vendor**: 선호하는 공급업체(Vendor)를 선택합니다. 특별한 선호가 없다면 `Microsoft OpenJDK` 또는 `Eclipse Temurin`을 선택하는 것을 권장합니다.
    * **Location**: 설치될 경로를 확인합니다. (보통 기본 경로를 그대로 사용합니다.)

    설정이 완료되면 `Download` 버튼을 클릭합니다.

IntelliJ가 자동으로 JDK 21을 다운로드하고 설치한 뒤, SDK 목록에 추가해 줍니다. 이제 프로젝트에서 Java 21을 사용할 모든 준비가 끝났습니다!  
  

### **1단계: Spring Boot 프로젝트 생성**

먼저, MCP 서버의 기반이 될 Spring Boot 프로젝트를 생성합니다. Git Bash 터미널에서 아래 `curl` 명령어를 실행하면 필요한 설정이 적용된 프로젝트가 `calculator-server.zip` 파일로 다운로드됩니다.

```bash
curl https://start.spring.io/starter.zip \
  -d dependencies=web \
  -d javaVersion=21 \
  -d type=maven-project \
  -d groupId=com.example \
  -d artifactId=calculator-server \
  -d name=McpServer \
  -d packageName=com.microsoft.mcp.sample.server \
  -o calculator-server.zip
```  

다운로드된 파일의 압축을 풀고 해당 디렉토리로 이동합니다. 
UI 상에서 진행을 하셔도 되고, 아래 명령어를 Git Bash에 입력해서도 수행할 수 있습니다.  
```bash
unzip calculator-server.zip -d calculator-server
cd calculator-server
# optional remove the unused test
rm -rf src/test/java
```

이제 프로젝트의 의존성을 관리하는 `pom.xml` 파일을 수정해야 합니다. 파일 전체를 아래 내용으로 교체해 주세요. Spring AI와 MCP 서버 관련 라이브러리를 추가하는 설정입니다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Spring Boot parent for dependency management -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.0</version>
        <relativePath />
    </parent>

    <!-- Project coordinates -->
    <groupId>com.example</groupId>
    <artifactId>calculator-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>Calculator Server</name>
    <description>Basic calculator MCP service for beginners</description>

    <!-- Properties -->
    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
    </properties>

    <!-- Spring AI BOM for version management -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.ai</groupId>
                <artifactId>spring-ai-bom</artifactId>
                <version>1.0.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <!-- Dependencies -->
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-mcp-server-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-test</artifactId>
         <scope>test</scope>
      </dependency>
    </dependencies>

    <!-- Build configuration -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <release>21</release>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <!-- Repositories for Spring AI snapshots -->
    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>https://repo.spring.io/snapshot</url>
            <releases>
                <enabled>false</enabled>
            </releases>
        </repository>
    </repositories>
</project>
```

### **2단계: 핵심 코드 작성**

프로젝트 설정이 끝났으니, 이제 서버의 실제 기능을 구현할 차례입니다.

#### **1. 의존성 설치**
먼저 `pom.xml`에 추가한 라이브러리들을 아래 명령어로 다운로드하고 프로젝트를 빌드합니다.  <br>  
<img width="682" height="786" alt="image" src="https://github.com/user-attachments/assets/16e4da96-38ef-417a-acb7-d0bcc5485ffd" />  <br>

```bash
cd calculator-server
./mvnw clean install -DskipTests
```

#### **2. 메인 애플리케이션 클래스 수정**
`src/main/java/com/microsoft/mcp/sample/server/McpServerApplication.java` 파일을 열어, 잠시 후에 만들 `CalculatorService`를 MCP 도구로 등록하는 코드를 추가합니다.

```java
package com.microsoft.mcp.sample.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.microsoft.mcp.sample.server.service.CalculatorService;

@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }
    
    @Bean
    public ToolCallbackProvider calculatorTools(CalculatorService calculator) {
        return MethodToolCallbackProvider.builder().toolObjects(calculator).build();
    }
}
```
  

#### **3. 계산기 서비스(도구) 구현**
`src/main/java/com/microsoft/mcp/sample/server/service/CalculatorService.java` 파일을 새로 만들고, 사칙연산 등 계산기 기능을 구현합니다. 각 메서드에 `@Tool` 어노테이션을 붙이면 외부에서 호출할 수 있는 MCP 도구가 됩니다.

```java
package com.microsoft.mcp.sample.server.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * Service for basic calculator operations.
 * This service provides simple calculator functionality through MCP.
 */
@Service
public class CalculatorService {

    /**
     * Add two numbers
     * @param a The first number
     * @param b The second number
     * @return The sum of the two numbers
     */
    @Tool(description = "Add two numbers together")
    public String add(double a, double b) {
        double result = a + b;
        return formatResult(a, "+", b, result);
    }

    /**
     * Subtract one number from another
     * @param a The number to subtract from
     * @param b The number to subtract
     * @return The result of the subtraction
     */
    @Tool(description = "Subtract the second number from the first number")
    public String subtract(double a, double b) {
        double result = a - b;
        return formatResult(a, "-", b, result);
    }

    /**
     * Multiply two numbers
     * @param a The first number
     * @param b The second number
     * @return The product of the two numbers
     */
    @Tool(description = "Multiply two numbers together")
    public String multiply(double a, double b) {
        double result = a * b;
        return formatResult(a, "*", b, result);
    }

    /**
     * Divide one number by another
     * @param a The numerator
     * @param b The denominator
     * @return The result of the division
     */
    @Tool(description = "Divide the first number by the second number")
    public String divide(double a, double b) {
        if (b == 0) {
            return "Error: Cannot divide by zero";
        }
        double result = a / b;
        return formatResult(a, "/", b, result);
    }

    /**
     * Calculate the power of a number
     * @param base The base number
     * @param exponent The exponent
     * @return The result of raising the base to the exponent
     */
    @Tool(description = "Calculate the power of a number (base raised to an exponent)")
    public String power(double base, double exponent) {
        double result = Math.pow(base, exponent);
        return formatResult(base, "^", exponent, result);
    }

    /**
     * Calculate the square root of a number
     * @param number The number to find the square root of
     * @return The square root of the number
     */
    @Tool(description = "Calculate the square root of a number")
    public String squareRoot(double number) {
        if (number < 0) {
            return "Error: Cannot calculate square root of a negative number";
        }
        double result = Math.sqrt(number);
        return String.format("√%.2f = %.2f", number, result);
    }

    /**
     * Calculate the modulus (remainder) of division
     * @param a The dividend
     * @param b The divisor
     * @return The remainder of the division
     */
    @Tool(description = "Calculate the remainder when one number is divided by another")
    public String modulus(double a, double b) {
        if (b == 0) {
            return "Error: Cannot divide by zero";
        }
        double result = a % b;
        return formatResult(a, "%", b, result);
    }

    /**
     * Calculate the absolute value of a number
     * @param number The number to find the absolute value of
     * @return The absolute value of the number
     */
    @Tool(description = "Calculate the absolute value of a number")
    public String absolute(double number) {
        double result = Math.abs(number);
        return String.format("|%.2f| = %.2f", number, result);
    }

    /**
     * Get help about available calculator operations
     * @return Information about available operations
     */
    @Tool(description = "Get help about available calculator operations")
    public String help() {
        return "Basic Calculator MCP Service\n\n" +
               "Available operations:\n" +
               "1. add(a, b) - Adds two numbers\n" +
               "2. subtract(a, b) - Subtracts the second number from the first\n" +
               "3. multiply(a, b) - Multiplies two numbers\n" +
               "4. divide(a, b) - Divides the first number by the second\n" +
               "5. power(base, exponent) - Raises a number to a power\n" +
               "6. squareRoot(number) - Calculates the square root\n" + 
               "7. modulus(a, b) - Calculates the remainder of division\n" +
               "8. absolute(number) - Calculates the absolute value\n\n" +
               "Example usage: add(5, 3) will return 5 + 3 = 8";
    }

    /**
     * Format the result of a calculation
     */
    private String formatResult(double a, String operator, double b, double result) {
        return String.format("%.2f %s %.2f = %.2f", a, operator, b, result);
    }
}
```

#### 4. 운영 환경을 위한 권장 설정하기
실제 서비스 환경에 서버를 배포할 때는 안정성과 편의성을 높이기 위한 몇 가지 추가 설정을 하는 것이 좋습니다.  

**1) 서버 시작 정보 출력 (`StartupConfig.java`)**

서버가 시작될 때 터미널에 SSE 엔드포인트나 상태 확인(Health Check) URL 같은 유용한 정보를 자동으로 출력하도록 설정할 수 있습니다. `CommandLineRunner`를 사용하면 애플리케이션이 시작된 직후 원하는 코드를 실행할 수 있습니다.

```java
package com.microsoft.mcp.sample.server.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {
    
    @Bean
    public CommandLineRunner startupInfo() {
        return args -> {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Calculator MCP Server is starting...");
            System.out.println("SSE endpoint: http://localhost:8080/sse");
            System.out.println("Health check: http://localhost:8080/actuator/health");
            System.out.println("=".repeat(60) + "\n");
        };
    }
}
```

**2) 사용자 정의 상태 확인 API (`HealthController.java`)**

Spring Boot Actuator가 `/actuator/health` 엔드포인트를 기본으로 제공하지만, 때로는 서비스 상태, 현재 시간 등 우리가 원하는 정보만 담아 더 간단한 형태의 상태 확인 API를 직접 만들고 싶을 수 있습니다.

```java
package com.microsoft.mcp.sample.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", "Calculator MCP Server");
        return ResponseEntity.ok(response);
    }
}
```

**3. 전역 예외 처리기 (`GlobalExceptionHandler.java`)**

서비스 운영 중 예외가 발생했을 때, 정해진 형식의 깔끔한 에러 메시지를 클라이언트에게 보내주는 것은 매우 중요합니다. `@RestControllerAdvice`를 사용하면 여러 컨트롤러에서 발생하는 특정 예외를 한 곳에서 공통으로 처리할 수 있습니다.

```java
package com.microsoft.mcp.sample.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            "Invalid_Input", 
            "Invalid input parameter: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        // Getters
        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}
```

**4. 사용자 정의 배너 (`banner.txt`)**
`src/main/resources/` 폴더에 `banner.txt` 파일을 만들면, 서버가 시작될 때 기본 Spring 로고 대신 원하는 ASCII 아트와 텍스트를 표시할 수 있습니다. 서비스의 정체성을 보여주는 재미있는 방법입니다.

```txt
_____      _            _       _             
 / ____|    | |          | |     | |            
| |     __ _| | ___ _   _| | __ _| |_ ___  _ __ 
| |    / _` | |/ __| | | | |/ _` | __/ _ \| '__|
| |___| (_| | | (__| |_| | | (_| | || (_) | |   
 \_____\__,_|_|\___|\__,_|_|\__,_|\__\___/|_|   
                                                
Calculator MCP Server v1.0
Spring Boot MCP Application
```


### **3단계: 서버 실행 및 테스트**

이제 모든 준비가 끝났습니다. 서버를 실행하고 테스트해 봅시다.

#### **1. 서버 실행**
터미널에서 아래 명령어를 차례로 실행하여 프로젝트를 빌드하고 서버를 시작합니다.
또는 IntelliJ에서의 버튼을 눌러 빌드 및 실행을 할 수 있습니다.  

```java
./mvnw clean install -DskipTests
java -jar target/calculator-server-0.0.1-SNAPSHOT.jar
```
또는  <br>
<img width="812" height="511" alt="image" src="https://github.com/user-attachments/assets/9998c7c9-30f8-41c2-b283-bda6bec66d31" />  <br>  
  

서버가 정상적으로 실행되면 터미널에 Spring Boot 로고와 함께 시작 로그가 나타납니다.  
<img width="772" height="636" alt="image" src="https://github.com/user-attachments/assets/f3422539-a48d-4c11-bba2-828bd879a479" />  
  

#### **2. Inspector로 테스트하기**
**Inspector**는 실행 중인 MCP 서버에 연결해서 어떤 도구들이 있는지 확인하고 직접 호출하며 테스트할 수 있는 편리한 웹 기반 도구입니다.  
이 테스트는 **Node.js가 있어야 가능합니다.** **없는 경우 생략하셔도 되는 실습입니다.**

1.  계산기 서버가 실행 중인 상태에서 **새 터미널 창**을 열고 아래 명령어로 Inspector를 실행합니다.

    ```bash
    npx @modelcontextprotocol/inspector
    ```

2.  웹 브라우저에 Inspector 화면이 열리면, 서버 접속 정보를 다음과 같이 입력합니다.
    * **Transport Type**: `SSE` 선택
    * **URL**: `http://localhost:8080/sse` 입력
3.  **`Connect`** 버튼을 클릭하여 서버에 연결합니다.  
<br>
   <img width="1918" height="907" alt="image" src="https://github.com/user-attachments/assets/630b2e06-99ab-433e-8766-ace4ae4ee968" />  <br>  


연결에 성공하면 Inspector 화면에서 `calculatorService`에 구현한 `add`, `subtract` 같은 도구 목록을 확인할 수 있습니다. 도구를 선택하고 필요한 숫자 값을 입력한 뒤 실행하면 서버로부터 받은 결과가 화면에 표시됩니다.

이제 여러분만의 Java 기반 MCP 서버를 성공적으로 구축하고 테스트까지 완료했습니다!
