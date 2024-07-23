package com.project.dreamingAnimal.controller;

import com.project.dreamingAnimal.dto.UserDto;
import com.project.dreamingAnimal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 로그인 form
    @GetMapping("/loginForm")
    public String login(@RequestParam(value = "error", required = false) String error, Model model){
        model.addAttribute("error",error);
        return "user/loginForm";
    }

    // 회원가입 Form
    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(UserDto userDto){
        userService.save(userDto);
        return "redirect:/loginForm";
    }

    // 회원가입 시 id 중복 체크
    @PostMapping("/user/username-check")
    public @ResponseBody String usernameCheck(@RequestParam("username") String username) {
        String checkResult = userService.idCheck(username);
        return checkResult;
    }

    // 회원가입 시 email 중복 체크
    @PostMapping("/user/email-check")
    public @ResponseBody String emailCheck(@RequestParam("email") String email) {
        String checkResult = userService.emailCheck(email);
        return checkResult;
    }

    // 회원가입 시 nickname 중복 체크
    @PostMapping("/user/nickname-check")
    public @ResponseBody String nickCheck(@RequestParam("nickname") String nickname) {
        String checkResult = userService.nicknameCheck(nickname);
        return checkResult;
    }

    // 수정 form
    @GetMapping("/updateForm")
    public String updateForm(){
        return "user/update";
    }

    // 회원 정보 수정
    @PostMapping("/update")
    public String update(UserDto userDto){
        userService.update(userDto);
        return "user/update";

    }

}
