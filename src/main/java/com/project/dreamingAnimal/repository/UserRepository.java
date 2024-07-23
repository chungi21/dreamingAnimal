package com.project.dreamingAnimal.repository;

import com.project.dreamingAnimal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    public UserEntity findByUsername(String username);

    public UserEntity findByEmail(String email);

    public UserEntity findByNickname(String nickname);
}
