package com.project.dreamingAnimal.entity;

import com.project.dreamingAnimal.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DA_USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String role;

    private String provider;
    private String providerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate; // 작성 시간

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 수정 시간

    public static UserEntity toUserEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userDto.getUsername());
        userEntity.setNickname(userDto.getNickname());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());

        return userEntity;
    }

    public static UserEntity toUserUpdateEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setNickname(userDto.getNickname());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());
        userEntity.setProvider(userDto.getProvider());
        userEntity.setProviderId(userDto.getProviderId());

        return userEntity;
    }

    @Builder
    public UserEntity(String username, String nickname, String password, String email, String role, String provider, String providerId) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

}





















