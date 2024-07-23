package com.project.dreamingAnimal.dto;

import com.project.dreamingAnimal.entity.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class UserDto {

    private int id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String role;
    private String provider;
    private String providerId;

    public static UserDto toUserDto(UserEntity userEntity){
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setNickname(userEntity.getNickname());
        userDto.setPassword(userEntity.getPassword());
        userDto.setEmail(userEntity.getEmail());
        userDto.setRole(userEntity.getRole());
        userDto.setProvider(userEntity.getProvider());
        userDto.setProviderId(userEntity.getProviderId());

        return userDto;
    }


}
