package com.project.dreamingAnimal.controller;

import com.project.dreamingAnimal.config.auth.PrincipalDetails;
import com.project.dreamingAnimal.dto.ResponseDto;
import com.project.dreamingAnimal.entity.CommunityReplyEntity;
import com.project.dreamingAnimal.service.CommnunityService;
import com.project.dreamingAnimal.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommunityApiController {

    @Autowired
    private CommnunityService communityService;

    @Autowired
    private ShelterService shelterService;

    // 댓글 작성
    @PostMapping("/communityV/{boardId}/replyW")
    public ResponseEntity<?> replySave(@PathVariable int boardId, @RequestBody CommunityReplyEntity reply, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        communityService.replyWrite(principalDetails.getUsername(), boardId, reply);
        ResponseDto<Integer> responseDto = new ResponseDto<>(HttpStatus.OK.value(), 1);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 수정
    @PostMapping("/commnunityV/{boardId}/replyU")
    public ResponseEntity<?> replayUpdate(@PathVariable int boardId, @RequestBody CommunityReplyEntity data, @AuthenticationPrincipal PrincipalDetails principalDetails){

        communityService.replyWrite(principalDetails.getUsername(),boardId,data);

        ResponseDto<Integer> responseDto = new ResponseDto<>(HttpStatus.OK.value(), 1);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @PostMapping("/commnunityV/{boardId}/replyD")
    public ResponseEntity<?> replayDelete(@PathVariable int boardId, @RequestBody CommunityReplyEntity data, @AuthenticationPrincipal PrincipalDetails principalDetails){

        communityService.replyDelete(data.getId());
        ResponseDto<Integer> responseDto = new ResponseDto<>(HttpStatus.OK.value(), 1);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}

