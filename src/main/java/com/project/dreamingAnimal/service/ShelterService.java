package com.project.dreamingAnimal.service;

import com.project.dreamingAnimal.dto.BoardFileDto;
import com.project.dreamingAnimal.dto.ShelterDto;
import com.project.dreamingAnimal.entity.BoardFileEntity;
import com.project.dreamingAnimal.entity.ShelterEntity;
import com.project.dreamingAnimal.repository.BoardFileRepository;
import com.project.dreamingAnimal.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private BoardFileRepository boardFileRepository;

    // 인증 요청 글쓰기
    public void save(ShelterDto shelterDto, MultipartFile file) throws Exception{

        ShelterEntity shelterEntity = ShelterEntity.toSaveShelterEntity(shelterDto);

        if(file !=null && !file.isEmpty()){ // 파일이 있을 경우
            shelterEntity.setFileAttached(1);
            ShelterEntity saveEntitiy = shelterRepository.save(shelterEntity);

            // 파일 이름가져오기
            String originalFileName = file.getOriginalFilename();
            // 저장용 파일 이름 만들기
            UUID uuid = UUID.randomUUID();
            String[] extension = originalFileName.split("[.]");
            int lastIdx = extension.length-1;
            String storedFileName = System.currentTimeMillis()+"-"+uuid+"."+extension[lastIdx];

            // boardFile DB에 넣을 BoardFileEntity 세팅
            BoardFileEntity boardFileEntity = new BoardFileEntity();
            boardFileEntity.setBoardId(saveEntitiy.getId());
            boardFileEntity.setOriginalFileName(originalFileName);
            boardFileEntity.setStoredFileName(storedFileName);
            boardFileEntity.setBoard("shelter");

            // 파일 저장용 폴더
            String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/shelter";

            // 파일 저장
            File saveFile = new File(savePath,storedFileName);
            file.transferTo(saveFile);

            // boardFile DB에 넣음
            boardFileRepository.save(boardFileEntity);
        }else{ // 파일이 없을 경우
            shelterEntity.setFileAttached(0);
            shelterRepository.save(shelterEntity);
        }
    }

    // 인증 승인하기
    public void save(ShelterDto shelterDto){
        ShelterEntity shelterEntity = ShelterEntity.toUpdateShelterEntity(shelterDto);
        System.out.println("shelterEntity : "+shelterEntity);
        shelterRepository.save(shelterEntity);
    }

    // 전체 리스트
    public Page<ShelterEntity> allList(Pageable pageable){
        return shelterRepository.findAll(pageable);
    }

    // 승인여부에 따른 리스트
    public Page<ShelterEntity> approvalList(int approval,Pageable pageable){
        return shelterRepository.findByApproval(approval,pageable);
    }

    // 검색 리스트
    public Page<ShelterEntity> searchList(String searchKeyword, Pageable pageable) {
        return shelterRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 검색과 승인여부 리스트
    public Page<ShelterEntity> searchApprovalList(String searchKeyword,int approval, Pageable pageable) {
        return shelterRepository.findByTitleContainingAndApproval(searchKeyword,approval, pageable);
    }

    // 보호소 정보보기 - pk로 가져와서 보기
    public ShelterEntity view(int id){
        return shelterRepository.findById(id).get();
    }

    // 보호소 정보 - 이미지 가져오기
    public List<BoardFileDto> getFileList(int id){
        List<BoardFileEntity> boardFileEntities = boardFileRepository.findByBoardIdAndBoard(id, "shelter");
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();


        for(BoardFileEntity boardFile : boardFileEntities){
            BoardFileDto boardFileDto = BoardFileDto.toBoardFileDto(boardFile);
            boardFileDtoList.add(boardFileDto);
        }


        return boardFileDtoList;
    }

    // 관리자 페이지에서 전체 리스트 보기
    public List<ShelterEntity> findAll(){
        List<ShelterEntity> shelterEntityList = shelterRepository.findAll();
        return shelterEntityList;
    }

    // 지도 범위에 따른 리스트 보기
    public List<ShelterEntity> findLonGreaterThanAndLonLessThanAndLatGreaterThanAndLatLessThan(double lon1,double lon2,double lat1,double lat2){
        List<ShelterEntity> shelterEntityList = shelterRepository.findByLonGreaterThanAndLonLessThanAndLatGreaterThanAndLatLessThanAndApproval(lon1,lon2,lat1,lat2,1);
        return shelterEntityList;
    }

    // 보호동물에서 보호센터 정보보기 - 작성자 정보로 보기
    public ShelterEntity findByUsername(String username){
        return shelterRepository.findByUsername(username);
    }

}
