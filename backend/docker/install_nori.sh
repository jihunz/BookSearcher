#!/bin/bash

# nori plugin이 설치되어있지 않다면 설치하는 스크립트

# /bin/bash /your path/install_nori.sh  로 해당 파일 실행

# es01 컨테이너로 접속하여 nori 설치 확인 -> 필요하다면 설치
  if [[ -z "$(docker exec es01 elasticsearch-plugin list)" ]]; then
    docker exec es01 bin/elasticsearch-plugin install analysis-nori
  fi


  if [[ -z "$(docker exec es02 elasticsearch-plugin list)" ]]; then
    docker exec es02 bin/elasticsearch-plugin install analysis-nori
  fi

# 각각의 컨테이너 재시작
docker-compose restart es01 es02
