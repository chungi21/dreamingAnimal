package com.project.dreamingAnimal.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class ProtectDto {
    private int id; // pk(id)
    private String sido; // 공고번호 시도
    private String sigungu; // 공고번호 시군구
    private String byYear; // 공고번호 년도
    private String num; // 공고번호 숫자
    private String kind; // 품종
    private String gender; // 품종
    private String color; // 색상
    private String age; // 나이
    private String weight; // 체중
    private String character; // 구조시 특징
    private String place; // 발견장소
    private String date; // 접수 일시
    private String username; // 작성자
    private int isProtect; // 보호 여부
    private String endReason; // 보호 종료 사유
    private int fileAttached; // 파일 첨부 여부
    private String imagePath; // 이미지 경로 추가


}
