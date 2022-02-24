# HTTP API 설계 예시



#### 회원 관리 시스템

**API 설계 - POST 기반 등록 (대부분 이를 사용함)**

- GET: 회원 목록 /members
- POST: 회원 등록 /members
- GET: 회원 조회 /members/{id}
- <u>PATCH</u>, PUT(게시글 수정정도), POST: 회원 수정 /members/{id}
- DELETE: 회원 삭제 /members/{id}

이 때 클라이언트는 등록될 리소스의 URI를 모른다. <u>서버가 새로 등록된 리소스 URI를 생성</u>해준다. 서버에 요청하는 느낌이다.

- 컬렉션(Collection)
  - 서버가 관리하는 리소스 디렉토리
  - 서버가 리소스의 URI를 생성하고 관리
  - 여기서 컬렉션은 /members



**API 설계 - PUT 기반 등록**

- GET: 파일 목록 /files
- GET: 파일 조회 /files/{filename}
- PUT: 파일 등록 /files/{filename}
- DELETE: 파일 삭제 /files/{filename}
- POST: 파일 대량 등록 /files

클라이언트가 리소스 URI를 알고 있어야 파일이 등록이 된다. 클라이언트가 관리하고 서버는 그냥 해주는 느낌이다.

- 스토어(Store)
  - 클라이언트가 관리하는 리소스 저장소
  - 클라이언트가 리소스의 URI를 알고 관리
  - 여기서 스토어는 /files



#### HTML FORM 사용

GET, POST만 지원한다. AJAX 같은 기술을 사용해서 해결이 가능하다.

**컨트롤 URI** (생각보다 많이 쓰게 된다.)

- GET, POST만 지원하므로 제약이 있음
- 이런 제약을 해결하기 위해 동사로 된 리소스 경로 사용
- POST의 /new, /edit, /delete가 컨트롤 URI (실무에서 많이 쓰이지만 대체제로 쓰이는 것)
- HTTP 메서드로 해결하기 애매한 경우 사용



[참고하면 좋은 URI 설계](https://restfulapi.net/resource-naming)사이트를 참조하면 좋을 수 있다.