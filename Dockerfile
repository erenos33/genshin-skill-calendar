# ✅ JDK 17 환경 사용
FROM eclipse-temurin:17-jdk

# ✅ 앱 폴더 지정
WORKDIR /app

# ✅ 현재 프로젝트 전체 복사
COPY src/main/java/me/genshin/skillcalendar .

# ✅ gradlew로 빌드 (Gradle wrapper 사용)
RUN ./gradlew build

# ✅ 완성된 jar 실행
CMD ["java", "-jar", "build/libs/skillcalendar-0.0.1-SNAPSHOT.jar"]
