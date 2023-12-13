# Novel-cloud
```
소설 연재 서비스 Novel-Cloud에서
일러스트, 만화, 소설 등을 올리고 공유하세요!
```

## 주 사용 기술
- Kotiln / Spring / JPA
- Authorization : Spring Security
- DOCS : Swagger
- DB : MySql, Redis
- AWS: EC2 t2.small, S3, CloudFront
- Test: junit, mockk

## 서버 아키텍쳐
<img width="1178" alt="image" src="https://github.com/Novel-Cloud/server/assets/45661217/c11680ff-c559-4bda-97e5-8cca005eeef0">

## AWS 아키텍쳐
<img width="577" alt="image" src="https://github.com/Novel-Cloud/server/assets/45661217/0b3f6758-79b1-4df2-8890-18c27771894c">

## 프로젝트 실행 방법
### MySQL 및 스키마 설정
MySQL 데이터베이스는 프로젝트 내부의 src/main/resources/sql/ddl.sql을 실행하여 스키마를 만들 수 있습니다.  
DB 스키마 설정이 완료되면 application.properties 파일에 설정한 환경 변수를 모두 입력해주세요.  

**프로젝트 실행하기**   
Novel-Cloud 서비스는 프론트와 백엔드가 분리되어있습니다.  
백엔드 서비스인 이 프로젝트를 먼저 실행한 후, 프론트 앱을 실행하면 앱 구동이 완료됩니다.  

Backend API Server (this repository).  
[Frontend repository](https://github.com/Novel-Cloud/app)

## ERD

![image](https://user-images.githubusercontent.com/45661217/231745662-370ec0d7-c1aa-4f02-b475-2b699b8be8c0.png)
