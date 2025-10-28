# 1단계: 빌드(Build) 환경
# Java 17과 Maven이 모두 포함된 이미지를 'builder'라는 별명으로 사용합니다.
FROM maven:3-eclipse-temurin-17 AS builder

# 작업 디렉터리를 생성합니다.
WORKDIR /app

# 의존성 캐싱을 위해 pom.xml과 Maven Wrapper 관련 파일들을 먼저 복사합니다.
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# mvnw 파일에 실행 권한을 부여합니다.
RUN chmod +x ./mvnw

# Maven 의존성을 다운로드합니다. 소스 코드가 변경되어도 이 부분은 캐시되어 재사용됩니다.
RUN ./mvnw dependency:go-offline

# 나머지 전체 소스 코드를 복사합니다.
COPY src ./src

# Maven Wrapper를 사용해 애플리케이션을 빌드(패키징)합니다. 테스트는 건너뛰어 빌드 속도를 높입니다.
RUN ./mvnw package -DskipTests


# 2단계: 최종 실행(Runtime) 환경
# 호환성이 높은 표준 Ubuntu 기반(Jammy)의 JRE 이미지를 사용합니다.
FROM eclipse-temurin:17-jre-jammy

# 작업 디렉터리를 생성합니다.
WORKDIR /app

# 빌드 단계('builder')에서 생성된 JAR 파일을 복사해옵니다.
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot 애플리케이션은 기본적으로 8080 포트를 사용합니다.
EXPOSE 8080

# 컨테이너가 시작될 때 실행할 명령어를 지정합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]