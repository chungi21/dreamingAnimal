package com.project.dreamingAnimal.service;

import com.project.dreamingAnimal.dto.BoardFileDto;
import com.project.dreamingAnimal.dto.CommunityDto;
import com.project.dreamingAnimal.entity.BoardFileEntity;
import com.project.dreamingAnimal.entity.CommunityEntity;
import com.project.dreamingAnimal.entity.CommunityReplyEntity;
import com.project.dreamingAnimal.repository.BoardFileRepository;
import com.project.dreamingAnimal.repository.CommnunityRepository;
import com.project.dreamingAnimal.repository.CommunityReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service
public class CommnunityService {

    @Autowired
    private CommnunityRepository commnunityRepository;

    @Autowired
    private BoardFileRepository boardFileRepository;

    @Autowired
    private CommunityReplyRepository communityReplyRepository;

    public void save(CommunityDto communityDto, List<MultipartFile> files) throws Exception{

        CommunityEntity communityEntity = CommunityEntity.toSaveCommnunityEntity(communityDto);

        // 여러 개의 파일을 가져올 경우
        if(files!=null){ // 파일이 있을 경우
            communityEntity.setFileAttached(1);
            CommunityEntity saveEntitiy = commnunityRepository.save(communityEntity);

            // 파일 이름가져오기
            for(MultipartFile file : files){

                // 원본 이름
                String originalFileName = file.getOriginalFilename();

                // 확장자 가져오기
                String[] extension = originalFileName.split("[.]");
                int lastIdx = extension.length-1;

                // 저장용 파일 이름 만들기
                UUID uuid = UUID.randomUUID();
                String storedFileName = System.currentTimeMillis()+"-"+uuid+"."+extension[lastIdx];

                // boardFile DB에 넣을 BoardFileEntity 세팅
                BoardFileEntity boardFileEntity = new BoardFileEntity();
                boardFileEntity.setBoardId(saveEntitiy.getId());
                boardFileEntity.setOriginalFileName(originalFileName);
                boardFileEntity.setStoredFileName(storedFileName);
                boardFileEntity.setBoard("community");

                // 파일 저장용 폴더
                String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/community";

                // 파일 저장
                File saveFile = new File(savePath,storedFileName);
                file.transferTo(saveFile);

                // boardFile DB에 넣음
                boardFileRepository.save(boardFileEntity);
            }

        }else{ // 파일이 없을 경우
            communityEntity.setFileAttached(0);
            commnunityRepository.save(communityEntity);
        }

    }

    public void save(CommunityDto communityDto){
        CommunityEntity communityEntity = CommunityEntity.toSaveCommnunityEntity(communityDto);
        communityEntity.setFileAttached(0);
        CommunityEntity saveEntitiy = commnunityRepository.save(communityEntity);
    }


    public CommunityEntity updateForm(int id){
        CommunityEntity communityEntity = commnunityRepository.findById(id).get();
        return communityEntity;
    }

    @Transactional
    public void update(CommunityDto communityDto,List<MultipartFile> files,List<Integer> del)throws Exception{
        CommunityEntity communityEntity = CommunityEntity.toUpdateCommnunityEntity(communityDto);

        // 조건
        // 새로 들어온 파일이 없고 삭제도 없을 때 => 파일 변경 없음.
        // 새로 들어온 파일이 없고 삭제만 있을 때
        // 새로 들어온 파일만 있고 삭제는 없을 때
        // 새로 들어온 파일이 있고 삭제도 있을 때
        // 조건을 정리하자면
        // 1. del 삭제해야할 파일이 있는가? => 있다면 orginalName, storedName 을 공백으로 변경
        if(del !=null){
            for(Integer id : del){
                boardFileRepository.gapUpdate(id);
            }
        }

        // 2. 새로 들어온 파일이 있는가?
        // 기존 파일 List
        List<BoardFileEntity> boardFileEntities = boardFileRepository.findByBoardIdAndBoard(communityDto.getId(), "community");

        // 2-1. 조건1. 기존파일 리스트 길이보다 작거나 같은경우 새로운 파일로 update
        if(files != null){ // 새 파일이 있을 때
            if(files.size()<=boardFileEntities.size()){
                for(int i=0;i<boardFileEntities.size();i++){

                    // 기존 해당 인덱스 부분에 새로운 파일을 set해주기
                    MultipartFile file = files.get(i);
                    if (!file.getOriginalFilename().isEmpty()) {
                        // 원본 이름
                        String originalFileName = file.getOriginalFilename();

                        // 확장자 가져오기
                        String[] extension = originalFileName.split("[.]");
                        int lastIdx = extension.length-1;

                        // 저장용 파일 이름 만들기
                        UUID uuid = UUID.randomUUID();
                        String storedFileName = System.currentTimeMillis()+"-"+uuid+"."+extension[lastIdx];

                        BoardFileEntity newFile = new BoardFileEntity();
                        newFile.setId(boardFileEntities.get(i).getId());
                        newFile.setBoard(boardFileEntities.get(i).getBoard());
                        newFile.setBoardId(boardFileEntities.get(i).getBoardId());
                        newFile.setOriginalFileName(originalFileName);
                        newFile.setStoredFileName(storedFileName);

                        boardFileEntities.set(i,newFile);

                        // 파일 저장용 폴더
                        String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/community";

                        // 파일 저장
                        File saveFile = new File(savePath,storedFileName);
                        file.transferTo(saveFile);

                    }
                }

                for(BoardFileEntity file : boardFileEntities){
                    boardFileRepository.update(file.getId(),file.getOriginalFileName(),file.getStoredFileName());
                }

            }else{ // 2-2. 조건2. 기존파일 리스트 길이보다 크다. 큰 부분만 insert
                for(int i=0;i<boardFileEntities.size();i++){
                    // 기존 해당 인덱스 부분에 새로운 파일을 set해주기

                    MultipartFile file = files.get(i);
                    if (!file.getOriginalFilename().isEmpty()) {
                        // 원본 이름
                        String originalFileName = file.getOriginalFilename();

                        // 확장자 가져오기
                        String[] extension = originalFileName.split("[.]");
                        int lastIdx = extension.length-1;

                        // 저장용 파일 이름 만들기
                        UUID uuid = UUID.randomUUID();
                        String storedFileName = System.currentTimeMillis()+"-"+uuid+"."+extension[lastIdx];

                        BoardFileEntity newFile = new BoardFileEntity();
                        newFile.setId(boardFileEntities.get(i).getId());
                        newFile.setBoard(boardFileEntities.get(i).getBoard());
                        newFile.setBoardId(boardFileEntities.get(i).getBoardId());
                        newFile.setOriginalFileName(originalFileName);
                        newFile.setStoredFileName(storedFileName);

                        boardFileEntities.set(i,newFile);

                        // 파일 저장용 폴더
                        String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/community";

                        // 파일 저장
                        File saveFile = new File(savePath,storedFileName);
                        file.transferTo(saveFile);
                    }
                }

                for(BoardFileEntity file : boardFileEntities){
                    boardFileRepository.update(file.getId(),file.getOriginalFileName(),file.getStoredFileName());
                }

                for(int i = boardFileEntities.size();i <files.size();i++){
                    MultipartFile file = files.get(i);
                    // 원본 이름
                    String originalFileName = file.getOriginalFilename();

                    // 확장자 가져오기
                    String[] extension = originalFileName.split("[.]");
                    int lastIdx = extension.length-1;

                    // 저장용 파일 이름 만들기
                    UUID uuid = UUID.randomUUID();
                    String storedFileName = System.currentTimeMillis()+"-"+uuid+"."+extension[lastIdx];

                    // boardFile DB에 넣을 BoardFileEntity 세팅
                    BoardFileEntity boardFileEntity = new BoardFileEntity();
                    boardFileEntity.setBoardId(communityDto.getId());
                    boardFileEntity.setOriginalFileName(originalFileName);
                    boardFileEntity.setStoredFileName(storedFileName);
                    boardFileEntity.setBoard("community");

                    // 파일 저장용 폴더
                    String savePath = System.getProperty("user.dir")+"/src/main/resources/static/img/community";

                    // 파일 저장
                    File saveFile = new File(savePath,storedFileName);
                    file.transferTo(saveFile);

                    // boardFile DB에 넣음
                    boardFileRepository.save(boardFileEntity);

                }
            }
        }

        // 3. DB에서 orginalName, storedName 을 공백인 컬럼은 삭제한다.
        boardFileRepository.deleteFile(communityDto.getId(),"community");

        // 4. 해당 게시물에 첨부파일이 있다면 community BBS table에 fileattached 를 1로 바꿔준다.
        boardFileEntities = boardFileRepository.findByBoardIdAndBoard(communityDto.getId(), "community");

        if(boardFileEntities == null || boardFileEntities.isEmpty()){
            communityEntity.setFileAttached(0);
        }else{
            communityEntity.setFileAttached(1);
        }

        commnunityRepository.save(communityEntity);
    }

    public Page<CommunityEntity> list(Pageable pageable){
        return commnunityRepository.findAll(pageable);
    }

    public Page<CommunityEntity> searchList(String searchKeyword, Pageable pageable) {
        return commnunityRepository.findByTitleContaining(searchKeyword, pageable);
    }

    @Transactional
    public CommunityEntity view(int id){

        CommunityEntity communityEntity = commnunityRepository.findById(id).get();

        // 조회수 올리기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // 로그인한 사용자의 정보를 이용하여 처리
            if(username!=communityEntity.getUsername()){
                commnunityRepository.updateConunt(id);
                // 위의 코드에 @transation을 이용하여 바로 DB는 변경이 되나 view페이지에는 +1 된 숫자로 세팅이 되지않으므로 위에 가져온 communityEntity의 count를 +1해서 다시 세팅해준다.
                // 사용자가 view페이지에 들어오는 순간 작성자가 아니라면 바로 +1해서 보여준다.
                communityEntity.setCount(communityEntity.getCount()+1);
            }
        }else {
            // 기획안에 첨은에는 로그인한 회원만을 하였기 떄문에 비로그인사용자는 따로 추가 처리하지는 않았다.
            commnunityRepository.updateConunt(id);
            communityEntity.setCount(communityEntity.getCount()+1);
        }

        return commnunityRepository.findById(id).get();
    }

    public List<BoardFileEntity> getFileList(int id){
        List<BoardFileEntity> boardFileEntities = boardFileRepository.findByBoardIdAndBoard(id, "community");
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        for(BoardFileEntity boardFile : boardFileEntities){
            BoardFileDto boardFileDto = BoardFileDto.toBoardFileDto(boardFile);
            boardFileDtoList.add(boardFileDto);
        }

        return boardFileEntities;
    }

    // 글 삭제
    @Transactional
    public void delete(int id){
        commnunityRepository.deleteById(id);
        boardFileRepository.deleteBbsFile(id,"community");

        // 실질적 저장 파일 삭제하기
        List<BoardFileEntity> boardFiles = getFileList(id);
        if(boardFiles!=null){
            for (BoardFileEntity s : boardFiles) {
                String srcFileName = null;
                String fileName = s.getStoredFileName();
                String uploadPath = System.getProperty("user.dir")+"/src/main/resources/static/img/community";
                try {
                    srcFileName = URLDecoder.decode(fileName,"UTF-8");
                    File file = new File(uploadPath + File.separator + srcFileName); // 매개변수 => 파일경로 이다.
                    boolean result = file.delete(); // true이면 지우기 성공, false면 지우기 실패
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // 댓글 작성자 정보 가져오기
    public CommunityReplyEntity replyById(int replyId){
        CommunityReplyEntity communityReplyEntity = communityReplyRepository.findById(replyId).get();
        return communityReplyEntity;

    }

    // 댓글 쓰기
    @Transactional
    public void replyWrite(String username, int boardId, CommunityReplyEntity requstReply){

        CommunityEntity communityEntity = commnunityRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패");
        });

        requstReply.setUsername(username);
        requstReply.setCBbs(communityEntity);

        communityReplyRepository.save(requstReply);
    }


    // 댓글 수정
    public void replyUpdate(String username, int boardId, CommunityReplyEntity requstReply){
        CommunityEntity communityEntity = commnunityRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("글 찾기 실패");
        });

        requstReply.setUsername(username);
        requstReply.setCBbs(communityEntity);
        communityReplyRepository.save(requstReply);

    }

    @Transactional
    public void replyDelete(int id){
        communityReplyRepository.deleteById(id);
    }

}
