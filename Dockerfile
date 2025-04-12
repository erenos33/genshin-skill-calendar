# ✅ Java 17 환경
FROM eclipse-temurin:17-jdk

# ✅ 앱 실행 폴더 생성 및 이동
WORKDIR /app

# ✅ gradlew 실행을 위한 권한 설정을 미리 해줘야 함 (중요!)
COPY . .

# ✅ gradlew 실행 권한 부여
RUN chmod +x ./gradlew

# ✅ 빌드 실행
RUN ./gradlew build

# ✅ jar 실행 (정확한 이름으로 수정 필요!)
CMD ["java", "-jar", "build/libs/skillcalendar-0.0.1-SNAPSHOT.jar"]
