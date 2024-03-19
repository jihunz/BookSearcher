# 1. 빌드
FROM openjdk:17-jdk-slim
RUN mkdir /home/app
RUN mkdir /home/image

FROM gradle:7.6.2-jdk17 as builder
WORKDIR /build

COPY ../build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY ../.. /build
RUN gradle clean bootjar -Pprofile=prod

# 2. jar 실행
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/build/libs/bookSearcher-0.0.1-SNAPSHOT.jar .

EXPOSE 8081
ENTRYPOINT [                                                \
    "java",                                                 \
    "-jar",                                                 \
    "-Djava.security.egd=file:/dev/./urandom",              \
    "-Dsun.net.inetaddr.ttl=0",                             \
    "bookSearcher-0.0.1-SNAPSHOT.jar"              \
]