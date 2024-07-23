package com.project.dreamingAnimal.repository;

import com.project.dreamingAnimal.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommnunityRepository extends JpaRepository<CommunityEntity, Integer> {

    // 리스트 검색어
    Page<CommunityEntity> findByTitleContaining(String searchKeyword, Pageable pageable);

    // 조회수 올리기
    @Modifying
    @Query(value=" update CommunityEntity c set c.count = c.count + 1 where c.id = :id ")
    void updateConunt(@Param("id") int id);

}
