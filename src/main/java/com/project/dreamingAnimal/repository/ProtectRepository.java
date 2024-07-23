package com.project.dreamingAnimal.repository;

import com.project.dreamingAnimal.entity.ProtectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProtectRepository extends JpaRepository<ProtectEntity,Integer>, JpaSpecificationExecutor<ProtectEntity> {

    // 승인 여부에 따라 리스트 보여주기
    Page<ProtectEntity> findByIsProtect(Pageable pageable,int isProtect);
}
