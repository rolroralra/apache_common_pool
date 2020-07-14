- [ ] Network Connection을 Pool 형태로 관리할 수 있는 모듈을 개발
  - [x] Pool Size는 사용자가 설정 가능
  
    [ObjectPoolConfig.java](https://github.com/rolroralra/apache_common_pool/blob/master/src/main/java/connection/pool/ObjectPoolConfig.java)
  
  - [ ] 여러 종류의 Connection 적용 가능
  
  - [ ] Connection Pool 사용자가 직접 코드를 작성하여 확장할 수 있는 구조
  
- [x] 설정 파일로 관리 (JSON)

- [x] 사용 완료된 리소스는 재사용 가능 (재사용을 위한 가장 효율적인 자료구조로 구현)

- [ ] 리소스에 대한 모니터링

- [ ] 사용자 이벤트 등으로 신규 리소스 할당을 중지할 수 있는 기능

- [x] Multi-Thread환경에서 안정적으로 사용할 수 있어야함

- [ ] 가용 리소스가 없을때 다양한 방식으로 처리 (Retry, Timeout, etc...)

- [ ] 의도하지 않은 예외 상황에서 Connection Pool 상태를 쉽게 확인할 수 있어야합니다.

- [x] Logback을 이용하여 로그 관리
  - [x] 로그파일을 하루 단위로 분리
  - [x] 로그레벨 적용
  - [x] 오류 발생 시, StackTrace 전체를 로그파일에 남깁니다.
  
- [x] Junit을 통한 단위 테스트 작성

