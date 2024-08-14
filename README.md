# 1st Toy Project - Dreaming Animal
> 웹 개발에 기본 인 게시판(파일첨부,댓글기능)과 회원가입을 만든 첫 번째 토이 프로젝트입니다.
> Spring Boot, JPA, SpringSecurity, 카카오지도 API, 다음 주소 검색을 이용하여 만들어 보았습니다.
![bbs_community_list](https://github.com/user-attachments/assets/6ad2a67a-fc6f-42b1-9a1f-e33dacf8a760)


## 목차
* [프로젝트 소개](#-프로젝트-소개)
* [프로젝트 개요](#-프로젝트-개요)
* [사용 기술](#-사용-기술)
  * [백엔드](#-백엔드)
  * [프론트엔드](#-프론트엔드)
* [설계](#-설계)
  * [DB 설계](#-DB-설계)
  * [API 설계](#-API-설계)
* [개발내용](#-개발내용)
* [실행화면](#-실행화면)
* [후기](#-후기)

## 프로젝트 소개
지금까지 학습한 내용을 바탕으로, 복습 겸 실습을 통해 내 것으로 만들기 위해 토이 프로젝트를 시작했습니다.<br>
웹 개발의 기본인 게시판을 직접 만들어 보면서, 부족한 부분을 파악하고 이해도를 확인하려는 목적이 있습니다.<br>
독학으로 관련 기술들을 배워 왔기에, 개인 프로젝트에서 미흡한 부분들이 있을 수 있습니다.<br>
이러한 부분들을 하나씩 점검하고 개선해 나가면서, 앞으로 프로그래밍에 있어 어떤 방향성을 가져야 할지 고민하고 발전해 나가려 합니다.<br>

## 프로젝트 개요
* 프로젝트 명 : Dreaming Animal
* 개발 인원 : 1 명
* 개발 기간 : 2024.03.31 ~ 2024.06.30
* 주요 기능 : 
  * 커뮤니티 게시판 : CRUD 기능과 댓글 기능을 제공 및 조회수 기능 구현
  * 보호소 게시판 : 글 작성(C)과 조회(R) 기능을 제공하며, 카카오 API를 활용해 작성자의 위치 정보를 표시 및 다음 주소 API를 이용해 정확한 주소 및 좌표 수집
(작성된 글의 수정 및 삭제는 관리자 페이지에서 관리)
  * 보호동물 게시판  : CRUD 기능과 페이징 및 검색 처리, 그리고 권한에 따른 글쓰기 기능을 구현
  * 사용자 관리 : Security를 활용한 회원가입 및 로그인 기능(네이버, 구글 연동), 일반 회원가입 및 로그인, 회원가입 시 유효성 검사와 중복 검사 기능을 구현
  * 관리자 페이지 : 보호소 게시판에서 작성된 글의 작성자에 대한 수정(R) 기능을 관리할 수 있는 페이지를 제공

## 사용 기술
### 백엔드
* 주요 프레임워크 / 라이브러리
  * JAVA17
  * Springboot 3.2.1
  * JPA(Spring Data JPA)
  * Spring Security
  * OAuth 2.0
 
* Build Tool
  * Maven
 
* DataBase
  * H2

### 프론트엔드
* HTML/CSS
* JavaScript
* Thymeleaf
* BootStarp 4.1.1


## 구조 설계

### DB 설계
* 커뮤니티 게시판 테이블(테이블 명 : DA_COMMUNITYBBS )<br>
![DA_COMMUNITY](https://github.com/user-attachments/assets/1113228d-c950-4443-ac99-34da7fa3dc43)<br>
* 커뮤니티 게시판 댓글 테이블(테이블 명 : DA_COMMUNITYREPLYBBS )<br>
![DA_REPLY](https://github.com/user-attachments/assets/b8624c6b-8b05-4246-91dd-5ab85532c404)<br>
* 보호소 게시판 테이블(테이블 명 : DA_SHELTERBBS )<br>
![DA_SHELTER](https://github.com/user-attachments/assets/9daf45e4-3ad1-4a69-9518-6cd344f250a8)<br>
* 보호동물 게시판 테이블(테이블 명 : DA_PROTECTBBS )<br>
![DA_PROTECT](https://github.com/user-attachments/assets/851f3938-2de4-4dfd-9337-3316c59b3871)<br>
* 사용자 테이블(테이블 명 : DA_USER )<br>
![DA_USER](https://github.com/user-attachments/assets/72726754-5be7-43a2-8fe3-57a7c8d5a4cf)<br>
* 파일 테이블(테이블 명 : DA_BOARDFILE )<br>
![DA_FILE](https://github.com/user-attachments/assets/9af01741-c681-40be-b03c-34aaa69fec23)<br>


### API 설계
* 커뮤니티 게시판 관련 API<br>
![커뮤니티 게시판](https://github.com/user-attachments/assets/a992a368-4638-4255-969c-e86b5193fcc7)<br>
* 커뮤니티 게시판 댓글 관련 API<br>
![커뮤니티 게시판 댓글](https://github.com/user-attachments/assets/bfacf1f2-d743-4514-819e-102fd6450420)<br>
* 보호소 게시판 관련 API<br>
![보호소 게시판](https://github.com/user-attachments/assets/4632e159-4b20-4f61-8403-f297a51a735c)<br>
* 보호소 게시판(관리자) 관련 API<br>
![보호소 게시판(관리자)](https://github.com/user-attachments/assets/f5c4e8fd-5331-4441-a782-044489a0226d)<br>
* 보호동물 게시판 관련 API<br>
![보호동물 게시판](https://github.com/user-attachments/assets/345ce336-f6ce-4c18-8e55-3c49d759d01d)<br>
* 사용자 관련 API<br>
![사용자 관련](https://github.com/user-attachments/assets/849313ce-8e5c-4697-9171-68f568206622)<br>


## 개발내용
개발내용에 대한 정리 내용을 링크로 걸어놨습니다. 
여러 글로 나눠 정리되어있는 부분은 걸려있는 블로그 링크의 글 아랫부분에 적어놨습니다.
* [Spring Security - 회원가입 및 로그인 구현](https://luckygirljinny.tistory.com/299)
* [Spring Security - 로그인 실패 시 에러메세지](https://luckygirljinny.tistory.com/318)
* [Spring Security - 권한 설정](https://luckygirljinny.tistory.com/301)
* [회원가입 유효성 검사](https://luckygirljinny.tistory.com/319)
* [OAuth 2.0 소셜로그인(구글, 네이버)](https://luckygirljinny.tistory.com/320)
* [게시판 CRUD 구현](https://luckygirljinny.tistory.com/321)
* [게시판 댓글 CRUD 구현](https://luckygirljinny.tistory.com/322)
* [게시판 페이징 구현](https://luckygirljinny.tistory.com/326)
* [게시판 검색처리 구현(JPA)](https://luckygirljinny.tistory.com/310)
* [게시판 검색처리 구현(동적 쿼리)](https://luckygirljinny.tistory.com/313)
* [게시판 파일 업로드](https://luckygirljinny.tistory.com/314)
* [Entity 연관관계 설정](https://luckygirljinny.tistory.com/323)
* [카카오 지도 API 사용](https://luckygirljinny.tistory.com/324)
* [다음 주소 API 사용](https://luckygirljinny.tistory.com/325)

## 실행화면
<details>
<summary>커뮤니티 게시판 관련 화면</summary>
  <br>
  <strong>1. 글 쓰기</strong>
  <ul>
    <li> 글 작성시 파일첨부가 가능합니다.</li>
    <li> 파일첨부는 이미지만 가능하며 파일 옆에 ‘+’ 버튼을 눌러 추가첨부가능하고 이미지를 넣지않을 경우 파일 첨부부분에 ‘x’버튼을 눌러 파일 첨부를 취소할 수 있습니다.</li>
    <li> 글 작성이 완료되면 목록페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/9cc3fc0e-a2be-46d6-bdf5-232f76dd64fb" alt="커뮤니티 게시판 글 쓰기">
  
  <br>
  <strong>2. 글 목록</strong>
  <ul>
    <li>전체 목록을 페이징 처리하여 조회가능합니다.</li>
    <li>검색어가 있다면 제목에 검색어가 포함되어있는 목록만 조회가 가능합니다.</li>
  </ul>
  
  <img src="https://github.com/user-attachments/assets/052584a7-0efb-420e-8a0b-a13f1de5dfdb" alt="커뮤니티 게시판 글 목록">

  <br>
  <strong>3. 글 상세페이지</strong>
  <ul>
    <li>상세페이지에서 글 쓴 내용이 보이며 ‘수정’,’삭제’버튼은 글쓴이 글 작성자에게만 보입니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/0bcabedc-e389-449f-9808-066766a1853c" alt="커뮤니티 게시판 글 상세페이지">

  <br>
  <strong>4. 글 수정하기</strong>
  <ul>
    <li>글 상세페이지에서 글쓴이 자신이 쓴 글이 댓글이 달려있지않은 경우 ‘수정’버튼을 눌러 수정페이지로 이동가능하며, 글 수정이 가능합니다.</li>
    <li>수정완료가 되면 전체 목록 페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/0a7816cf-095e-4ab0-a03a-852fd87be20f" alt="커뮤니티 게시판 글 수정하기">

  <br>
  <strong>5. 글 삭제하기</strong>
  <ul>
    <li>글 상세페이지에서 글쓴이 자신이 쓴 글이 댓글이 달려있지않은 경우 ‘삭제’버튼을 눌러 글 삭제가 가능합니다.</li>
    <li>삭제가 완료되면 전체 목록 페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/2704ccc7-6285-44be-86dc-8020c4a62318" alt="커뮤니티 게시판 글 삭제하기">
</details>

<details>
<summary>커뮤니티 게시판 댓글 관련 화면</summary>
  <br>
  <strong>1. 댓글 쓰기(비로그인 사용자 화면)</strong>
  <ul>
    <li>댓글은 로그인한 회원만 쓸 수 있도록 해놓았기 때문에 댓글 작성불가합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/f6e4bcb7-dc0d-4725-bd1e-fba0e4b36e28" alt="댓글 쓰기(비로그인 사용자 화면)">

  <br>
  <strong>2. 댓글 쓰기(로그인 사용자 화면)</strong>
  <ul>
    <li>댓글을 쓸 수 있는 textarea가 보입니다.</li>
    <li>로그인한 회원은 댓글을 작성할 수 있으며, 작성완료 시 현재 페이지를 reload합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/5470d53a-0c95-41d9-9f35-3830114a0454" alt="댓글 쓰기(로그인 사용자 화면)1">
  <img src="https://github.com/user-attachments/assets/41299a0a-36fe-4278-9012-6787f6274284" alt="댓글 쓰기(로그인 사용자 화면)2">
  <img src="https://github.com/user-attachments/assets/ff155d5e-d457-4c30-945b-6cd8eccfb2b4" alt="댓글 쓰기(로그인 사용자 화면)3">

  <br>
  <strong>3. 댓글 수정하기</strong>
  <ul>
    <li>자신이 쓴 댓글만 수정이 가능합니다. </li>
    <li>사용자 본인이 등록된 댓글이면 '수정','삭제'버튼이 보이고 '수정'버튼을 클릭하면 댓글쓰기 부분이 댓글 수정으로 변경됩니다. </li>
    <li>댓글 내용 수정이 완료되면 현재 페이지를 reload합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/104eb886-3554-4ee0-a5a2-a89516d39cb9" alt="댓글 수정하기1">
  <img src="https://github.com/user-attachments/assets/2a2e473b-85ad-48ab-976d-d51455940002" alt="댓글 수정하기2">

  <br>
  <strong>4. 댓글 삭제하기</strong>
  <ul>
    <li>자신이 쓴 댓글만 삭제가 가능합니다.</li>
    <li>사용자 본인이 등록된 댓글이면 '수정','삭제'버튼이 보이고 '삭제'버튼을 클릭하면 댓글이 삭제가 됩니다.. </li>
    <li>댓글 삭제가 완료되면 현재 페이지를 reload합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/4124b528-e8a5-4a69-bdb4-6ef055e84a9b" alt="댓글 삭제하기">
  
</details>

<details>
<summary>보호소 게시판 관련 화면</summary>
  <br>
  <strong>1. 글 쓰기(보호소 인증 글 쓰기)</strong>
  <ul>
    <li>보호소 인증 글쓰기를 할 때 주소입력시 다음주소API를 이용하여 주소 정보를 받습니다.</li>
    <li>정보 입력 후 ‘인증 요청하기’버튼을 클릭하면 글쓰기가 완료되고 목록 페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/c5dac97e-b002-47bf-82b2-1df3a2ec7715" alt="보호소 인증요청 글쓰기1">
  <img src="https://github.com/user-attachments/assets/469346e6-be12-48f3-aeba-46b28e049696" alt="보호소 인증요청 글쓰기2">
  <img src="https://github.com/user-attachments/assets/1ddb7910-f54b-4203-9545-0d79aff3e49d" alt="보호소 인증요청 글쓰기3">

  <br>
  <strong>2. 글 목록(리스트)</strong>
  <ul>
    <li>보호소 인증요청 글을 쓴 후 관리자가 승인을 한 보호소 인증요청글만 리스트에 보입니다.</li>
    <li>검색어에 보호소명을 넣어 검색하면 보호소명에 검색어가 포함된 보호소만 목록에 보입니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/c4aad6ab-e1cc-4306-bdca-270b9a29c435" alt="글 목록(리스트)">

  <br>
  <strong>3. 글 목록(지도)</strong>
  <ul>
    <li>보호소 인증요청 글을 쓴 후 관리자가 승인을 한 보호소 인증요청글만 지도에 보입니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/ac67234e-69e1-47cf-8d42-7d9e10c88a1a" alt="글 목록(지도)">

  <br>
  <strong>4. 글 상세페이지</strong>
  <ul>
    <li>리스트에서 상세페이지를 볼 경우에는 리스트를 클릭하면 클릭한 보호소의 정보가 모달로 보입니다.</li>
    <li>지도에서 상세페이지를 볼 경우에는 지도에 표시된 보호소를 클릭하면 클릭한 보호소의 정보가 모달로 보입니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/fda85baa-8467-44a4-a278-b568ab7153ff" alt="글 상세페이지(리스트)">
  <img src="https://github.com/user-attachments/assets/aba72645-c320-4a99-be72-575877f9c8a1" alt="글 상세페이지(지도)">
</details>

<details>
<summary>보호소 승인(관리자) 관련 화면</summary>
  <br>
  <strong>1. 글 목록</strong>
  <ul>
    <li>승인요청한 보호소 전체 글 목록을 페이징 처리하여 조회가능합니다.</li>
    <li>전체, 승인여부에 따라 목록을 조회가능합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/02392237-2b11-4a19-a8f5-7a7a938d411e" alt="보호소 글 목록(관리자)">

  <br>
  <strong>2. 글 상세페이지</strong>
  <ul>
    <li>글 목록에서 '정보보기'를 클릭하여 해당 글(보호소 인증 요청)의 정보를 모달로 확인가능합니다. </li>
  </ul>
  <img src="https://github.com/user-attachments/assets/b5524721-2a30-46d3-aaac-6261391b3bdd" alt="보호소 글 상세페이지(관리자)">

  <br>
  <strong>3. 글 수정하기(보호소 승인하기)</strong>
  <ul>
    <li>상세페이지에서 ‘승인하기’버튼을 클릭하면 해당 글(인증 요청 보호소)이 승인이되며 목록페이지가 reload됩니다.</li>
    <li>승인된 글(인증 요청 보호소)의 작성자는 등급이 CENTER로 변경됩니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/25897b4d-ba48-4a7c-aca6-f96db1b235ad" alt="보호소 글 수정하기(관리자)1">
  <img src="https://github.com/user-attachments/assets/541ef72f-f968-4fdf-b91c-6c052f782b08" alt="보호소 글 수정하기(관리자)1">
</details>

<details>
<summary>보호동물 게시판 관련 화면</summary>
  <br>
  <strong>1. 글 쓰기(보호동물 등록하기)</strong>
  <ul>
    <li>보호동물 등록가능한 글 쓰기는 보호소승인요청 후 승인된 회원만 작성가능합니다.</li>
    <li>글쓰기(등록)이가 완료되면 보호동물 목록페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/c1ffdb09-fca2-4725-b5fe-d8c9f4c26002" alt="보호동물 글 쓰기">

  <br>
  <strong>2. 글 목록</strong>
  <ul>
    <li>전체 글 목록조회가 가능합니다.</li>
    <li>공고번호, 접수일시, 품종, 보호상황, 성별에 따라 검색 가능합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/9b7e77e6-0385-4bd3-80da-016c15c71715" alt="보호동물 글 목록">

  <br>
  <strong>3. 글 상세페이지</strong>
  <ul>
    <li>글 상세페이지에서는 보호동물 등록된 글 외에도 글쓴이의 보호소정보도 같이 확인 가능합니다.</li>
    <li>글쓴이 본이이라면 수정, 삭제, 보호종료(보호종료만 수정)가 가능합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/840fd4fb-959c-44ec-b941-9c3b6e2f2772" alt="보호동물 글 상세페이지">

  <br>
  <strong>4. 글 수정하기</strong>
  <ul>
    <li>글 상세페이지에서 ‘수정’버튼을 클릭하여 수정페이지에 들어가 수정가능하며 수정완료가 되면 목록페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/ddfcf7d7-bcc8-4167-a094-94abdabe4ca4" alt="보호동물 글 수정하기">

  <br>
  <strong>5. 글 수정하기(보호동물 보호종료)</strong>
  <ul>
    <li>글 상세페이지에서 ‘보호종료’버튼을 클릭하면 보호종료 사유를 적을 수 있는 모달 창이 뜨며 모달창에 ‘보호종료’버튼을 클릭하면 보호종료상태로 변경되며 목록페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/0cb82fa7-b3a1-40fd-96b3-19a8926a6829" alt="보호동물 글 수정하기(보호종료)1">
  <img src="https://github.com/user-attachments/assets/788c8b53-6248-46ae-b394-2f90a11dd4ba" alt="보호동물 글 수정하기(보호종료)2">

  <br>
  <strong>6. 글 삭제하기</strong>
  <ul>
    <li>글 상세페이지에서 ‘삭제’버튼을 클릭하여 삭제가 가능하며 삭제 후에는 목록페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/1ffcde5a-ee22-466b-ad1b-eeb8cba54da1" alt="보호동물 글 삭제하기">
</details>

<details>
<summary>사용자(회원가입,로그인) 관련 화면</summary>
  <br>
  <strong>1. 회원가입하기</strong>
  <ul>
    <li>회원가입 시 유효성 검사 및 중복확인을 진행하며 완료시 회원정보를 저장하고 로그인 페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/44b5eda1-2cd3-4de0-81a7-18a2a450fcb1" alt="회원가입하기1">
  <img src="https://github.com/user-attachments/assets/aa075be9-d295-41f4-b102-c9626ff0a5d7" alt="회원가입하기2">
  <img src="https://github.com/user-attachments/assets/605488e3-5725-4caa-88c8-a83d812e4f01" alt="회원가입하기3">
  <img src="https://github.com/user-attachments/assets/1cd4e54a-f947-4628-8064-bdb665c378b0" alt="회원가입하기4">

  <br>
  <strong>2. 로그인하기</strong>
  <ul>
    <li>로그인 실패하면 어떤 이유로 실패했는지 메세지가 화면에 나오고, 로그인 성공하면 메인 페이지로 이동합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/b0d4c202-a53f-4bdb-99c9-774ecd74f012" alt="로그인하기1">
  <img src="https://github.com/user-attachments/assets/c80ec17e-759a-4bc7-803e-681229cc2ed0" alt="로그인하기2">

  <br>
  <strong>3. OAuth 2.0 소셜 로그인하기(네아로, 구글로그인)</strong>
  <ul>
    <li>로그인 페이지에서 '구글로그인', '네이버로그인'을 클릭하면 네이버계정과 구글계정으로 회원가입 및 로그인이 가능합니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/c04b14a4-2642-4b20-b261-638bd9c96c98" alt="소셜 로그인하기(네아로)">
  <img src="https://github.com/user-attachments/assets/9be1ac46-b552-46f4-a62a-0674ee77d75b" alt="소셜 로그인하기(구글)">

  <br>
  <strong>4. 회원정보 수정하기</strong>
  <ul>
    <li>로그인한 회원의 자기 자신의 정보만 수정가능하며, 회원정보 수정 시에는 비밀번호와 닉네임만 변경할 수 있고, 변경된 닉네임이 이미 사용중일 경우 alert으로 알려주며, 회원수정페이지로 redirect로 하는데 변경된 정보로 보이게 됩니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/57d4008a-e849-4c2c-b1ab-6030a52a3f31" alt="회원정보 수정하기">
</details>

<details>
<summary>메인 화면</summary>
  <br>
  <strong></strong>
  <ul>
    <li>메인화면은 보호동물 게시판, 보호소 게시판(지도,목록), 커뮤니티 게시판으로 바로 갈 수 있는 세션으로 이루어져 있습니다.</li>
  </ul>
  <img src="https://github.com/user-attachments/assets/3c1add58-11d0-41bb-bc8f-38dc13c2af5f" alt="메인화면">
</details>

## 후기
* 이번 프로젝트는 독학 후 처음으로 진행한 것이라, 기획 단계에서부터 부족한 점이 있었습니다. 처음에는 미흡했던 부분을 보완하고자 중간중간 새로운 기능을 추가하기도 했고, 반대로 기능적으로 겹치는 부분은 빼기로 결정하며 아쉬움을 느끼기도 했습니다.
* 코드를 작성하면서는 어떤 상황에서 GET과 POST를 사용하는 것이 맞을지, 그리고 @Controller와 @RestController 중 어떤 것을 선택해야 할지, 각 로직에서 어느 시점까지 DTO로 받아야 하고, 어디서 Entity로 변환해야 할지 등 다양한 부분 등 깊이 고민했습니다. 하지만 이러한 과정에서 단순히 현재의 추세를 따르기보다는, 왜 그런 추세가 생겨났는지, 그리고 그것이 실제로 어떻게 쓰이는지에 대해 이해하려고 노력했습니다. 그렇게 이유를 붙여가며 최선의 선택을 했습니다.
* 이번 프로젝트를 통해 아직 배워야 할 부분이 많다는 것을 깨달았습니다. 하지만 스스로 정리하는 시간을 가지면서, 어느 부분이 부족한지, 그리고 앞으로 어떤 방향으로 나아가야 할지에 대한 나름의 방향성을 찾을 수 있었습니다.
* 제가 좋아하는 말은 “노력이 완벽을 만든다”입니다. 여기서 말하는 노력에는 꾸준히 계속하는 것도 포함되지만, 노력은 곧 경험이라는 생각이 듭니다. 앞으로도 꾸준히 더 많은 경험을 쌓아가며 발전해 나가겠습니다.

