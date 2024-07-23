package com.project.dreamingAnimal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DA_BoardFile")
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // pk
    private String board; // 어떤 board에서 가져왔는지

    // community BBS 의 pk 저장
    // 어노테이션을 이용하여 연관관계를 맺지않음
    // why? board table에 community BBS 말고도 다른 BBS의 파일도 저장할 것이기 때문이다.
    private int boardId; // board pk
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 저장 파일 이름

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성시간

    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
