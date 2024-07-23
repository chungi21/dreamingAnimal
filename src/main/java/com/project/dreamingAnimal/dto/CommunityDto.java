package com.project.dreamingAnimal.dto;

import com.project.dreamingAnimal.entity.CommunityEntity;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class CommunityDto {

    private int id; // pk
    private String title; // 제목
    private String content; // 내용
    private int count; // 조회수
    private String username; // 작성자
    private int fileAttached; // 파일 첨부 여부

    public static CommunityDto toCommunityDto(CommunityEntity communityEntity){
        CommunityDto communityDto = new CommunityDto();

        communityDto.setFileAttached(communityEntity.getFileAttached());
        communityDto.setContent(communityEntity.getContent());
        communityDto.setUsername(communityEntity.getUsername());
        communityDto.setTitle(communityEntity.getTitle());
        communityDto.setFileAttached(communityEntity.getFileAttached());
        communityDto.setId(communityEntity.getId());
        communityDto.setCount(communityEntity.getCount());

        return communityDto;
    }
}
