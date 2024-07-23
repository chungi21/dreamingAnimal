package com.project.dreamingAnimal.repository;

import com.project.dreamingAnimal.entity.CommunityReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityReplyRepository extends JpaRepository<CommunityReplyEntity,Integer> {

}
