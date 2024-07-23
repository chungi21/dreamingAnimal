package com.project.dreamingAnimal.repository;

import com.project.dreamingAnimal.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity,Integer> {

    // 파일 변경
    // 첨부되어있는 파일 삭제에 체크박스에 체크 했을 경우
    @Modifying
    @Query(value=" update BoardFileEntity c set c.originalFileName = '', c.storedFileName = ''  where c.id = :id  ")
    void gapUpdate(@Param("id") int id);


    // 파일 변경
    // 첨부되어있는 파일 삭제에 체크 했고 새 파일을 넣을 경우 or 해당 새 파일 넣는 경우 => 새 파일을 넣는 경우
    @Modifying
    @Query(value=" update BoardFileEntity c set c.originalFileName = :originalFileName, c.storedFileName = :storedFileName  where c.id = :id ")
    void update(@Param("id") int id,@Param("originalFileName") String originalFileName,@Param("storedFileName") String storedFileName);

    // 첨부되어있는 파일 삭제에 체크박스에 체크 했고 새 파일이 들어오지않은경우 - c.originalFileName = '' and c.storedFileName = '' 빈칸으로 변경했고 불필요한 데이터이므로 삭제
    // 두 번 나눠서 삭제이유는 기존 파일은 냅둬야하기때문에 구분하고자 '' 으로 변경 후 삭제함.
    @Modifying
    @Query(value=" delete BoardFileEntity c where c.boardId = :boardId and c.originalFileName = '' and c.storedFileName = '' and c.board = :board ")
    void deleteFile(@Param("boardId") int boardId,@Param("board") String board);

    // boardId 와 board를 통해 파일 정보 삭제
    // boardId만 있을 경우 다른 테이블에 pk인 boardId도 삭제 될 수 있기 때문에 board(protect,shelter,community)인지 확인
    @Modifying
    @Query(value=" delete BoardFileEntity c where c.boardId = :boardId and c.board = :board")
    void deleteBbsFile(@Param("boardId") int boardId,@Param("board") String board);

    List<BoardFileEntity> findByBoardIdAndBoard(int id,String board);


}



