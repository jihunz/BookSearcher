#!/bin/bash

# 2. synonyms api를 사용하여 동의어 사전을 api로 수정하기 위해 기본 동의어 사전을 등록하는 스크립트
# /bin/bash /your path/install_nori.sh  로 해당 파일 실행

# 동의어 세트 추가
curl -XPUT -u elastic:12341234 "http://localhost:3333/_synonyms/desc_synonym" -H "kbn-xsrf: reporting" -H "Content-Type: application/json" -d'
{
  "synonyms_set": [
    {
      "id": "방법",
      "synonyms": "방법, 노하우"
    }
  ]
}'
