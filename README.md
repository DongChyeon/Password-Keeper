# Password-Keeper
#### 비밀번호 저장해서 관리하는 어플 만들기 프로젝트

<img width="300" src="https://user-images.githubusercontent.com/64844115/103433485-efcafc80-4c34-11eb-87b0-0f1649070f23.png">&emsp;&emsp;<img width="300" src="https://user-images.githubusercontent.com/64844115/103433515-83043200-4c35-11eb-9477-b0b6c59b117c.png">

#### 처음 설치 시 사용할 비밀번호를 정함. 다음 실행부터는 처음에 설정한 비밀번호 또는 지문인식을 통해 로그인 가능 (지문인식이 지원되지 않을 경우 비밀번호로만 로그인 가능)

<img width="300" src="https://user-images.githubusercontent.com/64844115/103433609-6832bd00-4c37-11eb-9869-324c012e9f2b.png">&emsp;&emsp;<img width="300" src="https://user-images.githubusercontent.com/64844115/103433611-68cb5380-4c37-11eb-99d2-115c5506214a.png">

#### 스와이프를 통한 카테고리 선택 가능 (현재 카테고리는 사이트, 카드 밖에 없음 이후 추가 확장 예정)

<img width="250" src="https://user-images.githubusercontent.com/64844115/103433630-ff981000-4c37-11eb-8f39-19edd460d9d8.png">&emsp;<img width="250" src="https://user-images.githubusercontent.com/64844115/103433633-00c93d00-4c38-11eb-97c4-4aa75862dbe9.png">&emsp;<img width="250" src="https://user-images.githubusercontent.com/64844115/103433632-0030a680-4c38-11eb-91f2-dc35d8714807.png">

#### 선택한 카테고리에서 아이템의 추가, 수정, 삭제 가능

<hr>

#### 데이터베이스의 경우 Room을 이용했고, 버전 업데이트에 대한 데이터베이스의 내용 이전 역시 Room의 Migration 클래스를 통해 구현
