package com.project.dreamingAnimal.dto;

import com.project.dreamingAnimal.entity.BoardFileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardFileDto {
    private int id; // pk
    private String board; // 어떤 board에서 가져왔는지
    private int boardId; // board pk
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 저장 파일 이름

    public static BoardFileDto toBoardFileDto(BoardFileEntity boardFileEntity){
        BoardFileDto boardFileDto = new BoardFileDto();

        boardFileDto.setId(boardFileEntity.getId());
        boardFileDto.setBoard(boardFileEntity.getBoard());
        boardFileDto.setBoardId(boardFileEntity.getBoardId());
        boardFileDto.setOriginalFileName(boardFileEntity.getOriginalFileName());
        boardFileDto.setStoredFileName(boardFileEntity.getStoredFileName());

        return boardFileDto;
    }

}
