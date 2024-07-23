package com.project.dreamingAnimal.entity;

import com.project.dreamingAnimal.dto.CommunityDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DA_CommunityBbs")
public class CommunityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // pk

    @Column(nullable = false,length = 100)
    private String title; // 제목

    @Lob
    private String content; // 내용

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int count; // 조회수
    private String username; // 작성자

    @OrderBy("id desc")
    @OneToMany(mappedBy = "CBbs",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<CommunityReplyEntity> reply;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성시간

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    private int fileAttached; // 파일 첨부 여부

    public static CommunityEntity toSaveCommnunityEntity(CommunityDto communityDto){
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setTitle(communityDto.getTitle());
        communityEntity.setContent(communityDto.getContent());
        communityEntity.setUsername(communityDto.getUsername());
        return communityEntity;
    }

    public static CommunityEntity toUpdateCommnunityEntity(CommunityDto communityDto){
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setId(communityDto.getId());
        communityEntity.setTitle(communityDto.getTitle());
        communityEntity.setContent(communityDto.getContent());
        communityEntity.setUsername(communityDto.getUsername());
        communityEntity.setCount(communityDto.getCount());

        return communityEntity;
    }

}

