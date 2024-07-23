package com.project.dreamingAnimal.controller;

import com.project.dreamingAnimal.dto.ShelterDto;
import com.project.dreamingAnimal.entity.ShelterEntity;
import com.project.dreamingAnimal.service.BoardFileService;
import com.project.dreamingAnimal.service.ShelterService;
import com.project.dreamingAnimal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private BoardFileService boardFileService;

    @Autowired
    private UserService userService;

    // 보호소 지도로 보기
    @GetMapping("/ShelterM")
    public String map(){
        return "shelter/map";
    }

    // 보호소 리스트로 보기
    @GetMapping("/ShelterL")
    public String list(Model model, @PageableDefault(page = 0, size = 15,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword){
        Page<ShelterEntity> list = null;

        if(searchKeyword == null) { // 검색 키워드가
            list = shelterService.approvalList(1,pageable);
        }else {
            list = shelterService.searchApprovalList(searchKeyword, 1, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "shelter/list";
    }

    // 보호소 인증 요청 Form
    @GetMapping("/ShelterW")
    public String writeForm(Model model){
        return "shelter/write";
    }

    // 보호소 인증 요청 글쓰기
    @PostMapping("/ShelterW")
    public String write(ShelterDto shelterDto, MultipartFile file , Model model) throws  Exception{
        shelterService.save(shelterDto,file);

        model.addAttribute("message", "인증요청 완료되었습니다.");
        model.addAttribute("searchUrl", "/ShelterL");
        return "message/message";
    }

    // 관리자 보호소 승인
    @GetMapping("/admin/shelter")
    public String shelterApproval(Model model, @PageableDefault(page = 0, size = 15,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword,Integer approval){

        Page<ShelterEntity> list = null;

        System.out.println(approval);
        if(approval == null && searchKeyword == null) { // 승인 전체 , 키워드 없음
            list = shelterService.allList(pageable);
        }else if(approval == null && searchKeyword != null) { // 승인 전체, 키워드 있음
            list = shelterService.searchList(searchKeyword, pageable);
        }else if(approval == 1 && searchKeyword == null) { // 승인 1, 키워드 없음
            list = shelterService.approvalList(1,pageable);
        }else if(approval == 1 && searchKeyword != null) { // 승인 1, 키워드 있음
            list = shelterService.searchApprovalList(searchKeyword,1,pageable);
        }else if(approval == 0 && searchKeyword == null) { // 승인 0, 키워드 없음
            list = shelterService.approvalList(0,pageable);
        }else{ // 승인 0, 키워드 있음
            list = shelterService.searchApprovalList(searchKeyword,0,pageable);
        }

        model.addAttribute("approval", approval);
        model.addAttribute("searchKeyword", searchKeyword);


        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "adm/list";
    }

    // 보호소 승인하기
    @PostMapping("/approval")
    public String shelterApproval(ShelterDto shelterDto, String searchKeyword,Integer approval,Model model){
        shelterService.save(shelterDto);
        userService.updateRole(shelterDto.getUsername());

        model.addAttribute("message", "해당 보호소가 승인완료 되었습니다.");
        model.addAttribute("searchUrl", "/admin/shelter");
        return "message/message";
    }

}

