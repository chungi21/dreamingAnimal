package com.project.dreamingAnimal.entity;

import com.project.dreamingAnimal.dto.ProtectDto;
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
@Table(name = "DA_ProtectBbs")
public class ProtectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // pk(id)

    private String sido; // 공고번호 시도
    private String sigungu; // 공고번호 시군구
    private String byYear; // 공고번호 년도
    private String num; // 공고번호 숫자
    private String kind; // 품종
    private String gender; // 성별
    private String color; // 색상
    private String age; // 나이
    private String weight; // 체중
    private String character; // 구조시 특징
    private String place; // 발견장소
    private String date; // 접수 일시
    private String username; // 작성자

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int isProtect; // 보호 여부

    private String endReason; // 보호 종료 사유
    private int fileAttached; // 파일 첨부 여부

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성 시간

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 수정 시간

    public static ProtectEntity toSaveProtectEntity(ProtectDto protectDto){
        ProtectEntity protectEntity = new ProtectEntity();
        protectEntity.setSido(protectDto.getSido());
        protectEntity.setSigungu(protectDto.getSigungu());
        protectEntity.setByYear(protectDto.getByYear());
        protectEntity.setNum(protectDto.getNum());
        protectEntity.setKind(protectDto.getKind());
        protectEntity.setGender(protectDto.getGender());
        protectEntity.setColor(protectDto.getColor());
        protectEntity.setAge(protectDto.getAge());
        protectEntity.setWeight(protectDto.getWeight());
        protectEntity.setCharacter(protectDto.getCharacter());
        protectEntity.setPlace(protectDto.getPlace());
        protectEntity.setDate(protectDto.getDate());
        protectEntity.setUsername(protectDto.getUsername());
        protectEntity.setIsProtect(protectDto.getIsProtect());
        protectEntity.setFileAttached(protectDto.getFileAttached());
        return protectEntity;
    }

}
