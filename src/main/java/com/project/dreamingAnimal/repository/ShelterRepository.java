package com.project.dreamingAnimal.repository;

import com.project.dreamingAnimal.entity.ShelterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<ShelterEntity, Integer> {
    Page<ShelterEntity> findByApproval(int approval, Pageable pageable);

    Page<ShelterEntity> findByTitleContainingAndApproval(String searchKeyword,int i, Pageable pageable);

    Page<ShelterEntity> findByTitleContaining(String searchKeyword, Pageable pageable);

    public List<ShelterEntity> findByLonGreaterThanAndLonLessThanAndLatGreaterThanAndLatLessThanAndApproval(double lon1, double lon2, double lat1, double lat2, int isApproval);

    ShelterEntity findByUsername(String username);
}
