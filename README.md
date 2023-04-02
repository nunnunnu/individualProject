[노션에서 보기(서버 배포 기록 등 더 자세한 기록을 볼 수 있습니다.)](https://coordinated-sunset-4f9.notion.site/568a3d687d9a4666bbe69996d131b74f)
### 현재 aws를 이용해 서버 배포중입니다.
http://watermelonfront.s3-website.ap-northeast-2.amazonaws.com/

(test용 ID : user001 PWD : 123456)

# Water Melon
음악 스트리밍 서비스 워터멜론 프로젝트입니다.

기간: 2023년 2월 9일 → 2023년 3월 20일

총 작업인원 : 1명 (혼자서 백엔드, 프론트엔드 작업했습니다.)
# 프로젝트 설명
1. 사용한 기술 
* 백엔드
  * spring boot
  * jpa
  * java
  * spring security
  * redis
    access token을 재발급 하기 위해 발급한 refresh token을 저장하기 위해 사용하였습니다. refresh token을 DB에 저장할 수도 있었지만 재발급을 요청할때 마다 DB에서 비교하는 것이 비효율적으로 느껴져서 상대적으로 성능이 좋은 redis를 사용하는 방법을 도전해보았습니다.
  * mysql
  * querydsl
    선호 장르 조회 쿼리의 서브쿼리를 효율적으로 사용하기 위해 사용하였습니다.
  * gradle
* 프론트엔드
  * vueJs
  * bootstrap
* 사용한 외부 API 
  * spotifyAPI
    사이트의 음악차트 연동을 위해 사용하였습니다.
  * 카카오 소셜로그인
  * chartjs
    사용자가 많이 들은 장르를 표기하기 위해 사용하였습니다.
2. 설명
이용권이 있을 때 음원 스트리밍, 다운로드가 가능한 사이트입니다.
음원 스트리밍/다운로드 외에도 뮤직비디오 감상, 곡 좋아요, 앨범 평점, 앨범 댓글(답댓글), 아티스트 팬맺기, 많이 들은 곡 장르(차트), 플레이 리스트, 재생목록 등 많은 기능을 구현하여사용자가 불편함이 없도록 하는 것을 목적으로 구현하였습니다.
3. 시연 영상

![Video Label](http://img.youtube.com/vi/ZD6hauRA0Pw/0.jpg)] (https://youtu.be/ZD6hauRA0Pw)
---
### 기타 주소
[테이블 명세서 파일 (노션페이지)](https://coordinated-sunset-4f9.notion.site/42cbbe8076d6429e977a4cf67d2d14d2)

[작업일지 (노션페이지)](https://coordinated-sunset-4f9.notion.site/2309aad3b89042c398d15e6ebc20fe1d?v=ecb5cea5a34240feb0269b6ed9bd801e)
