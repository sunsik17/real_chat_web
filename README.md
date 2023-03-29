# 실시간 채팅 서비스

## 배포
AWS EC2 : 3.34.191.128 
port : 8081
(DB, Server 인스턴스 연동)

simpleTest_URL 
: GET http://3.34.191.128/ping

result
: pong


## 회원
### 회원가입
- [x] 중복이메일 사용 불가
### 회원탈퇴
- [x] 참여한 채팅방에서 '이름없음' 으로 바뀜
### 로그인
- [x] JWT를 이용한 인증
- [x] 토큰 만료시간이 되면 갱신해야함


## 채팅
- [x] Stomp 연결

## 채팅방
- [x] 채팅방안에 있는 유저 관리
- [x] 채팅방엔 N명 구성 가능
- [x] 해당 채팅방 이전 기록 불러오기 가능
- [x] 나의 모든 채팅방 불러오기 가능

# erd
<img width="685" alt="image" src="https://user-images.githubusercontent.com/117346927/228537104-79ce4ce3-be0a-44a0-84ee-a748fc4c969f.png">

## 사용 해본 기술
- JPA
- WebSocket
- Springboot
- Java
- Hibernate
- MariaDB
- STOMP
- AWS
- Docker
