package com.project.dreamingAnimal.service;

import com.project.dreamingAnimal.dto.BoardFileDto;
import com.project.dreamingAnimal.dto.ProtectDto;
import com.project.dreamingAnimal.entity.BoardFileEntity;
import com.project.dreamingAnimal.entity.ProtectEntity;
import com.project.dreamingAnimal.repository.BoardFileRepository;
import com.project.dreamingAnimal.repository.ProtectRepository;
import com.project.dreamingAnimal.specification.ProtectSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProtectService {

    @Autowired
    private ProtectRepository protectRepository;

    @Autowired
    private BoardFileRepository boardFileRepository;

    // 글쓰기
    public void save(ProtectDto protectDto, MultipartFile file) throws Exception{

        ProtectEntity protectEntity = ProtectEntity.toSaveProtectEntity(protectDto);

        if(file !=null && !file.isEmpty()){ // 파일이 있을 경우
            protectEntity.setFileAttached(1);
            ProtectEntity saveEntitiy = protectRepository.save(protectEntity);

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
            boardFileEntity.setBoard("protect");

            // 파일 저장용 폴더
            String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/protect";

            // 파일 저장
            File saveFile = new File(savePath,storedFileName);
            file.transferTo(saveFile);

            // boardFile DB에 넣음
            boardFileRepository.save(boardFileEntity);
        }else{ // 파일이 없을 경우
            protectEntity.setFileAttached(0);
            protectRepository.save(protectEntity);
        }
    }

    // 리스트
    public Page<ProtectDto> listWithImages(Pageable pageable) {
        Page<ProtectEntity> protectEntities = protectRepository.findAll(pageable);
        return protectEntities.map(this::convertToDto);
    }

    // 리스트 - 메인
    // 리스트
    public Page<ProtectDto> mainListWithImages(Pageable pageable) {
        Page<ProtectEntity> protectEntities = protectRepository.findByIsProtect(pageable,0);
        return protectEntities.map(this::convertToDto);
    }

    // 리스트 - 검색했을 경우
    @Transactional(readOnly = true)
    public Page<ProtectDto> listWithImagesAndSerach(Pageable pageable, String protectNum, String kind, String gender, String date,Integer isProtect) {
        Specification<ProtectEntity> spec = Specification.where(null); // 초기 빈 Specification

        if (protectNum != null && !protectNum.isEmpty()) {
            String[] protectStr = protectNum.split("-");
            if (protectStr.length == 4) {
                String sido = protectStr[0];
                String sigungu = protectStr[1];
                String byYear = protectStr[2];
                String num = protectStr[3];

                spec = spec.and(ProtectSpecifications.hasSido(sido))
                        .and(ProtectSpecifications.hasSigungu(sigungu))
                        .and(ProtectSpecifications.hasByYear(byYear))
                        .and(ProtectSpecifications.hasNum(num));
            }
        }

        if (isProtect != null) {
            spec = spec.and(ProtectSpecifications.hasIsProtect(isProtect));
        }

        if (kind != null && !kind.isEmpty()) {
            spec = spec.and(ProtectSpecifications.hasKind(kind));
        }

        if (gender != null && !gender.isEmpty()) {
            spec = spec.and(ProtectSpecifications.hasGender(gender));
        }

        if (date != null && !date.isEmpty()) {
            spec = spec.and(ProtectSpecifications.hasDate(date));
        }

        Page<ProtectEntity> protectEntities = protectRepository.findAll(spec, pageable);

        return protectEntities.map(this::convertToDto);
    }


    private ProtectDto convertToDto(ProtectEntity protectEntity) {
        ProtectDto protectDto = new ProtectDto();

        protectDto.setId(protectEntity.getId());
        protectDto.setSido(protectEntity.getSido());
        protectDto.setSigungu(protectEntity.getSigungu());
        protectDto.setByYear(protectEntity.getByYear());
        protectDto.setNum(protectEntity.getNum());
        protectDto.setKind(protectEntity.getKind());
        protectDto.setColor(protectEntity.getColor());
        protectDto.setGender(protectEntity.getGender());
        protectDto.setAge(protectEntity.getAge());
        protectDto.setWeight(protectEntity.getWeight());
        protectDto.setCharacter(protectEntity.getCharacter());
        protectDto.setPlace(protectEntity.getPlace());
        protectDto.setDate(protectEntity.getDate());
        protectDto.setUsername(protectEntity.getUsername());
        protectDto.setIsProtect(protectEntity.getIsProtect());
        protectDto.setEndReason(protectEntity.getEndReason());
        protectDto.setFileAttached(protectEntity.getFileAttached());

        // 파일 경로 설정
        List<BoardFileEntity> files = boardFileRepository.findByBoardIdAndBoard(protectEntity.getId(),"protect");
        if (files != null && !files.isEmpty()) {
            protectDto.setImagePath(files.get(0).getStoredFileName());
        }

        return protectDto;
    }

    // 글 정보 보기
    @Transactional
    public ProtectEntity view(int id){
        return protectRepository.findById(id).get();
    }

    // 파일 가져오기
    public List<BoardFileDto> getFileList(int id){
        List<BoardFileEntity> boardFileEntities = boardFileRepository.findByBoardIdAndBoard(id, "protect");
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        for(BoardFileEntity boardFile : boardFileEntities){
            BoardFileDto boardFileDto = BoardFileDto.toBoardFileDto(boardFile);
            boardFileDtoList.add(boardFileDto);
        }
        return boardFileDtoList;
    }

    // 글 삭제
    @Transactional
    public void delete(int id){
        protectRepository.deleteById(id);
        boardFileRepository.deleteBbsFile(id,"protect");
    }

    // 수정 - 글 정보가져오기
    public ProtectEntity updateForm(int id){
        ProtectEntity protectEntity = new ProtectEntity();
        protectEntity = protectRepository.findById(id).get();
        return protectEntity;
    }

    // 수정 - 수정하기
    @Transactional
    public void update(ProtectEntity updateProtectEntity,List<MultipartFile> files)throws Exception{

        if(files.get(0).isEmpty()){ // 파일이 없는 경우
            protectRepository.save(updateProtectEntity);
        }else{ // 파일이 있는 경우
            // 기존 파일 DB에서 정보 가져오기
            List<BoardFileEntity> Dbfiles = boardFileRepository.findByBoardIdAndBoard(updateProtectEntity.getId(),"protect");

            for( BoardFileEntity boardFileEntity : Dbfiles ){
                MultipartFile file = files.get(0); // 파일이 하나이므로
                int boardTablePk = boardFileEntity.getId();

                String originalFileName = files.get(0).getOriginalFilename();

                // 확장자 가져오기
                String[] extension = originalFileName.split("[.]");
                int lastIdx = extension.length-1;

                // 저장용 파일 이름 만들기
                UUID uuid = UUID.randomUUID();
                String storedFileName = System.currentTimeMillis()+"-"+uuid+"."+extension[lastIdx];

                // 파일 저장용 폴더
                String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/protect";

                // 파일 저장
                File saveFile = new File(savePath,storedFileName);
                file.transferTo(saveFile);

                boardFileRepository.update(boardTablePk,originalFileName,storedFileName);
                protectRepository.save(updateProtectEntity);
            }

            // 기존 파일 DB정보를 update 해주기

        }
    }

    // 수정 - 보호종료 했을 때
    public void end(ProtectEntity protectEntity){
        protectRepository.save(protectEntity);
    }

}















































