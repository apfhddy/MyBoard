# MyBoard ( 게시판 프로젝트 )

# 들어가기 앞서 - 왜 흔한 게시판일까?

- 아마 프로젝트명을 보시면서 이생각을 하셨을 겁니다.
이번 프로젝트의 목표는 단순한 CRUD를 구현하고자가 아닙니다.
- 이번 프로젝트의 큰 목표는 기능에 대한 리스크를 생각하고 구현하자가 주된 목표이며 
개발중에 다양한 트러블 슈팅 및 성능최적화에 대한 고민에 초점을 두었습니다.

# 프로젝트 목표

- reverse proxy구조를 구현하여  ws, was의 역할 분리
- nginx의 로드밸런서를 사용하여 분산 트래픽 처리
- Jmeter을 사용하여 최대 400명의 환경에서의 성능 최적화

# 개발 환경
### Server
- **ws** : nginx
- **was** : spring boot > apache tomcat
- **Protocol** : HTTPS
  

### Back-End
- **Frameword** : Spring 3.9.14
- **Language** : JAVA
- **DataBase** : Oracle, Redis
- **Connect** : Mybatis

### Front-End
- JavaScript, Css, HTML
- **FormEditor** : summernote
- **Template** Engin : Thymeleaf

### Librarys
- Thymeleaf
- Spring Security
- Spring mail

# 개발 중 일어난 문제
- [이메일 리소스관리 최적화](https://reliable-butternut-fa4.notion.site/1a73b3da01c5801994bec51b8839671b)

