package com.project.dreamingAnimal.controller;

import com.project.dreamingAnimal.dto.ProtectDto;
import com.project.dreamingAnimal.service.ProtectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class IndexController {


    @Autowired
    private ProtectService protectService;

    // 메인 페이지
    @GetMapping({"","/"})
    public String index(Model model, Principal principal,@PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ProtectDto> protectList = protectService.mainListWithImages(pageable);
        model.addAttribute("protectList", protectList.getContent());

        return "main/index";
    }

    //  error page 401
    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied/accessDenied";
    }

    //  error page 403
    @GetMapping("/accessDenied2")
    public String accessDenied2(){
        return "accessDenied/accessDenied2";
    }





}
