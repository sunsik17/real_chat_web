FROM amazoncorretto:11
COPY build/libs/real_time_chat-0.0.1-SNAPSHOT.jar real_time_chat-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","real_time_chat-0.0.1-SNAPSHOT.jar"]