## JACOCO 정리 문서

### 테스트 고려사항
- DTO, VO(ENTITY) 중복로직검사
  - DTO = 비지니스, 서비스레이어
  - VO(ENTITY) = 영속성레이어 
  - 레이어별로 검증을 하는것도 맞다고 판단
- validation group
- DTO refactoring
  - 기능별 request, response 각각의 DTO 분할 
  - 명칭 **DTO 통일 (validation check)
  - 응답 객체의 경우 **ResponseDTO 통일 (validation x)


#### Build 명령어
```cmd
./gradlew --console verbose test jacocoTestReport jacocoTestCoverageVerification
```

#### 참고문서
```text
https://techblog.woowahan.com/2661/ (우아한 블로그)
https://ww.youtube.com/watch?v=FCTYoce21OI&ab_channel=%EC%96%B4%EB%9D%BC%EC%9A%B4%EB%93%9C%ED%97%88%EB%B8%8C%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4-AroundHubStudio (어라운드 허브 스튜디오)
https://meetup.toast.com/posts/223 (NHN Validation)
```

#### Version
```text
https://github.com/jacoco/jacoco (jacoco github)
0.8.8 : Java 17 and 18 supports
0.8.7 : Java 15 and 16 supports
```

#### Execution 내부에 사용되는 값
- prepare-agent : 테스트 중인 어플리케이션에서 인수를 전달하는 Jacoco Runtime Agent에 대한 property를 준비
- merge : 여러 실행 데이터 파일들을 하나로 통합하는 명령어
- report : 하나의 프로젝트 테스트에 대한 Code Coverage 리포트를 생성하는 명령어
- check : code coverage metric이 충돌하는지 확인하는 명령어

#### Element Type - 코드 커버리지 체크기준
- BUNDLE (default) : 패키지 번들
- PACKAGE : 패키지
- SOURCEFILE : 소스파일
- METHOD : 메소드

#### Count - 코드 커버리지를 측적할 때 사용하는 지표
- LINE : 빈 줄을 제외한 실제 코드의 라인 수
- BRANCH : 조건문 등의 분기 수
- CLASS : 클래스 수
- METHOD : 메소드 수
- INSTRUCTION (default) : Java 바이트 코드 명령 수
- COMPLEXITY : 복잡도

#### Value - 커버리지 정도를 나타내는 지표
- TOTALCOUNT : 전체개수
- MISSEDCOUNT : 커버되지 않은 개수
- COVEREDCOUNT : 커버된 개수
- MISSEDRATIO : 커버되지 않은 비율 (0~1)
- COVEREDRATIO (default) : 커버된 비율 (0~1)

#### 특정 클래스 제외
- CONFIG, FILTER : 통합테스트에서 수행한다.

- 제외방법
```java
//어노테이션 사용 (Lombok 라이브러리)
@Generated
public class DeleteJacocoTestClass {} 
```
```xml
<!--maven-->
<configuration>
 <excludes>
  <exclude> **/DeleteJacocoTestClass.class</exclude>
 </excludes>
</configuration>
```
```
//gradle
jacocoTestCoverageVerification {
  violationRules {
    rule {
      element = 'CLASS'

      limit {
        counter = 'LINE'
        value = 'TOTALCOUNT'
        maximum = 8
      }

      // 커버리지 체크를 제외할 클래스들
      excludes = [
        '*.test.*',
        '*.Kotlin*'
      ]
    }
  }
}
```

#### Maven 라이프 사이클 확인
Jacoco Plugin : Maven, Gradle Life Cycle 에 의해 동작
- test phase 이후에 측정 가능
- package phase 이후로 동작가능
```text
compile -> test -> package -> install -> deploy

```


