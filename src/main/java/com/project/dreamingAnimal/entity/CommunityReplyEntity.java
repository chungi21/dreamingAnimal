package com.project.dreamingAnimal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DA_CommunityReplyBbs")
public class CommunityReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // pk

    @Column(nullable = false,length = 200)
    private String content; // 답변 내용

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="boardId")
    private CommunityEntity CBbs; // community BBS 에서 어떤 보드인지 확인하기 위한 community BBS PK 값

    private String username; // 작성자 이름

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성 시간

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 수정 시간
}

























