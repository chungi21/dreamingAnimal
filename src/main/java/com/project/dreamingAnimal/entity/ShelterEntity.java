package com.project.dreamingAnimal.entity;

import com.project.dreamingAnimal.dto.ShelterDto;
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
@Table(name = "DA_ShelterBbs")
public class ShelterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // pk

    @Column(nullable = false,length = 100)
    private String title; // 보호소명

    @Lob
    private String content; // 소개
    private String address; // 주소
    private String address2; // 상세주소
    private double lon; // 경도
    private double lat; // 위도
    private String tel; // 전화번호

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int count; // 조회수
    private String username; // 작성자
    private int fileAttached; // 파일 첨부 여부

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int approval; // 승인 여부

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성 시간

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 수정 시간

    public static ShelterEntity toSaveShelterEntity(ShelterDto shelterDto){
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setUsername(shelterDto.getUsername());
        shelterEntity.setTitle(shelterDto.getTitle());
        shelterEntity.setContent(shelterDto.getContent());
        shelterEntity.setAddress(shelterDto.getAddress());
        shelterEntity.setAddress2(shelterDto.getAddress2());
        shelterEntity.setLon(shelterDto.getLon());
        shelterEntity.setLat(shelterDto.getLat());
        shelterEntity.setTel(shelterDto.getTel());
        return shelterEntity;
    }

    public static ShelterEntity toUpdateShelterEntity(ShelterDto shelterDto){
        ShelterEntity shelterEntity = new ShelterEntity();
        shelterEntity.setId(shelterDto.getId());
        shelterEntity.setUsername(shelterDto.getUsername());
        shelterEntity.setTitle(shelterDto.getTitle());
        shelterEntity.setContent(shelterDto.getContent());
        shelterEntity.setAddress(shelterDto.getAddress());
        shelterEntity.setAddress2(shelterDto.getAddress2());
        shelterEntity.setLon(shelterDto.getLon());
        shelterEntity.setLat(shelterDto.getLat());
        shelterEntity.setTel(shelterDto.getTel());
        shelterEntity.setApproval(shelterDto.getApproval());
        return shelterEntity;
    }


}
