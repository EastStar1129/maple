## CI/CD 구현 

#### 환경설정 
- CI : Jenkins
- Environment : Synology Docker


#### Jenkins 설치
- url : https://hub.docker.com/r/jenkins/jenkins
- 비고 : Official Image 아니다. (시간 엄청날림) 

```cmd
docker pull jenkins/jenkins:lts
```

#### Jenkins 설정
1. director mount : /var/jenkins_home
2. Port 변경 (8080 -> 49999)
3. Port 변경 (50000 -> 50000)
4. https 포트 설정 (49998)
5. reverse proxy 설정 (http -> https)
6. 계정 설정
   - JenkinsMaple / maplepass

#### 주요 플로그인
- Gradle Plugin ( gradle build )
- Post build task ( build log 를 판단해 batch/shell 실행 )
- GitHub API Plugin ( GitHub 와 연동 )
- Slack Configuration ( 빌드 실패 시 Slack 호출 )
- Jacoco (Test Coverage)

#### git 연동
- github token 발급 (https://kitty-geno.tistory.com/88)
  - token : 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 (90 days)
- webhook 연결 
  - synology https 설정 후 등록 