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
- synology https 설정
- github token 발급 (https://kitty-geno.tistory.com/88)
  - token : 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 (90 days)
- github webhook 등록
  - https://jangdonggyu.synology.me:49998/github-webhook/
  - application/x-www-form-urlencoded
  - Secret (x)
  - Enable SSL verification
  - Just the push event
- Jenkins 설정
- GitHub project : https://github.com/EastStar1129/maple/
- Repository URL : https://github.com/EastStar1129/maple.git
- Branch : develop
- 빌드유발 > GitHub hook trigger for GITScm polling
- 아래의 token 에 대한 slack 인증 추가
- 빌드 > slack
  - 하위도메인 : workspace name
  - slack channel : #개발자
  - 생성한 계정
  - build fail, success 시 send message of slack 
- Slack 설정
- 앱 > Jenkins CI 설치 > Workspace(하위도메인), token 저장
   