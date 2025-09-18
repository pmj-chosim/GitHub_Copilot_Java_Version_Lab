## 모듈 1, 2
```Dockefile
# 1. 베이스 이미지 선택
# 소스 코드를 컴파일해야 하므로 JDK(Java Development Kit) 버전 이미지를 사용합니다.
FROM eclipse-temurin:17-jdk-jammy

# 2. 작업 디렉터리 설정
# 컨테이너 내부에서 명령어가 실행될 기본 폴더를 지정합니다.
WORKDIR /app

# 3. 소스 코드 복사
# 로컬의 'src' 폴더를 컨테이너 안의 'src' 폴더로 복사합니다.
COPY src ./src

# 4. 자바 코드 컴파일
# javac 명령어로 src 폴더 안의 Main.java 파일을 컴파일합니다.
# 결과물인 Main.class 파일은 현재 작업 디렉터리(/app)에 생성됩니다.
RUN javac -d . src/Main.java

# 5. 애플리케이션 실행
# java 명령어로 컴파일된 Main 클래스를 실행합니다.
CMD ["java", "Main"]
```

*빌드  
```bash
docker build -t my-java-app .
``` 
또는
```bash
podman build -t my-java-app .
```

*실행
```bash
docker run --rm my-java-app
```
또는
```bash
podman run --rm my-java-app
```


---


