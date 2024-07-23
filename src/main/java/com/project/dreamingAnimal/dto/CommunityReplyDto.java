package com.project.dreamingAnimal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityReplyDto {
    private int id; // pk
    private String content; // 답변 내용
    private String username; // 작성자
}
