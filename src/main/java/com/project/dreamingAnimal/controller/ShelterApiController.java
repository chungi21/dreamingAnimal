package com.project.dreamingAnimal.controller;


import com.project.dreamingAnimal.dto.BoardFileDto;
import com.project.dreamingAnimal.entity.ShelterEntity;
import com.project.dreamingAnimal.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShelterApiController {

    @Autowired
    private ShelterService shelterService;

    // 보호소 상세
    @PostMapping("/shelterInfo/{id}")
    public Map<String,String> view(@PathVariable int id) {
        ShelterEntity shelterEntity = shelterService.view(id);

        Map<String,String> map = new HashMap<String,String>();
        String idStr = Integer.toString(id);
        map.put("id",idStr);
        map.put("title",shelterEntity.getTitle());
        map.put("content",shelterEntity.getContent());
        map.put("address",shelterEntity.getAddress());
        map.put("address2",shelterEntity.getAddress2());

        double lonVal = shelterEntity.getLon();
        String lonStr = Double.toString(lonVal);
        map.put("lon",lonStr);

        double latVal = shelterEntity.getLat();
        String latStr = Double.toString(latVal);
        map.put("lat",latStr);

        map.put("tel",shelterEntity.getTel());

        map.put("username",shelterEntity.getUsername());

        int fileAttacchedVal = shelterEntity.getFileAttached();
        String fileAttacchedStr = Integer.toString(fileAttacchedVal);
        map.put("fileAttached",fileAttacchedStr);


        int approvalVal = shelterEntity.getApproval();
        String approvalStr = Integer.toString(approvalVal);
        map.put("approval",approvalStr);

        List<BoardFileDto> boardFileDtos = shelterService.getFileList(id);
        for (BoardFileDto s : boardFileDtos) {
            String fileName = s.getStoredFileName();
            map.put("img",fileName);
        }

        return map;
    }

    // 지도 범위에 따른 보호소 리스트
    @PostMapping("/shelterMap/{lon1}/{lon2}/{lat1}/{lat2}")
    public List<ShelterEntity> shelerMap(@PathVariable String lon1,@PathVariable String lon2,@PathVariable String lat1,@PathVariable String lat2){

        double dLon1 = Double.parseDouble(lon1);
        double dLon2 = Double.parseDouble(lon2);
        double dLat1 = Double.parseDouble(lat1);
        double dLat2 = Double.parseDouble(lat2);
        List<ShelterEntity> shelterEntityList = shelterService.findLonGreaterThanAndLonLessThanAndLatGreaterThanAndLatLessThan(dLon1,dLon2,dLat1,dLat2);

        return shelterEntityList;
    }

}

