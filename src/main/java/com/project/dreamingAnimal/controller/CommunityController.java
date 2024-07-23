package com.project.dreamingAnimal.controller;

import com.project.dreamingAnimal.config.auth.PrincipalDetails;
import com.project.dreamingAnimal.dto.CommunityDto;
import com.project.dreamingAnimal.entity.BoardFileEntity;
import com.project.dreamingAnimal.entity.CommunityEntity;
import com.project.dreamingAnimal.entity.CommunityReplyEntity;
import com.project.dreamingAnimal.service.BoardFileService;
import com.project.dreamingAnimal.service.CommnunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private CommnunityService commnunityService;

    @Autowired
    private BoardFileService boardFileService;

    @GetMapping("/message")
    public String goUrl(){
        return "message/message";
    }

    // 리스트
    @GetMapping("/communityL")
    public String list(Model model, @PageableDefault(page = 0, size = 15,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword){
        Page<CommunityEntity> list = null;

        if(searchKeyword == null) {
            list = commnunityService.list(pageable);
        }else {
            list = commnunityService.searchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "community/list";
    }

    // 글쓰기 Form
    @GetMapping("/communityW")
    public String writeForm(){
        return "community/write";
    }

    // 글 작성
    @PostMapping("/communityW")
    public String write(CommunityDto communityDto, List<MultipartFile> files,Model model) throws Exception{

        commnunityService.save(communityDto,files);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/communityL");

        return "message/message";
    }

    // 글 수정 Form
    @GetMapping("/communityU/{id}")
    public String updateForm(@PathVariable int id,Model model){

        CommunityEntity communityEntity = commnunityService.updateForm(id);

        // 등록되어있던 글 정보 가져오기
        model.addAttribute("board",communityEntity);

        // 등록되어있던 글에 파일 정보 가져오기
        if(communityEntity.getFileAttached()==1){
            List<BoardFileEntity> boardFiles = commnunityService.getFileList(id); // Entity로 변경해서 받아오기
            model.addAttribute("boardFileList",boardFiles);
        }

        return "community/update";
    }

    // 글 수정
    @PostMapping("/communityU")
    public String update(CommunityDto communityDto, List<MultipartFile> files, @RequestParam (required = false) List<Integer> del,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
        commnunityService.update(communityDto,files,del);
        return "redirect:/communityL";
    }

    // 글 상세
    @GetMapping("/communityV/{id}")
    public String view(Model model,@PathVariable int id){

        // community에서 글 정보 가져오기
        CommunityEntity communityEntity =  commnunityService.view(id);
        model.addAttribute("board", communityEntity);

        // 저장 파일 가져오기
        List<BoardFileEntity> boardFiles = commnunityService.getFileList(id); // Entity로 변경하기
        model.addAttribute("boardFileList",boardFiles);

        // 댓글 가져오기
        // 댓글은 가져올 필요x
        // communityEntity를 보면
        // private List<CommunityReplyEntity> reply;
        // 이 부분을 통해 reply는 board 검색 시 그냥 가지고 오게 된다.
        // 그렇기 때문에 따로 가져오는 로직을 만들 필요가 없다.
        // 여기서 내가 말한 로직이란 service -> repository로 가는 로직을 말한다.
        List<CommunityReplyEntity> replies = communityEntity.getReply();
        // 각 댓글 내용 출력 model에 담기
        model.addAttribute("replies",replies);
        return "community/view";
    }


    //글 삭제
    @GetMapping("/communityD/{id}")
    public String delete(@PathVariable int id,Model model){
        // community table에서 해당 글 삭제하기
        // reply tabled에서 해당 글과 관련된 댓글 삭제하기
        // entity만들 때 연관관계에 의해 reply table에서 댓글은 같이 삭제된다.
        // file table에서 해당 게시글에 연관된 파일 삭제하기 file table에서 boardID 가 매개변수로 받은 id와 같으면 삭제하기
        commnunityService.delete(id);

        model.addAttribute("message", "글이 삭제가 되었습니다.");
        model.addAttribute("searchUrl", "/communityL");

        return "message/message";
    }


}

