package com.project.dreamingAnimal.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class ShelterDto {
    private int id; // pk
    private String title; // 보호소명
    private String content; // 소개 내용
    private String address; // 주소
    private String address2; // 상세 주소
    private double lon; // 경도
    private double lat; // 위도
    private String tel; // 전화번호
    private int count; // 조회수
    private String username; // 작성자
    private int fileAttached; // 파일 첨부 여부
    private int approval; // 승인 여부(승인 전 = 0, 승인 후 = 1)
}
