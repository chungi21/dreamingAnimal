package com.project.dreamingAnimal.controller;

import com.project.dreamingAnimal.config.auth.PrincipalDetails;
import com.project.dreamingAnimal.dto.BoardFileDto;
import com.project.dreamingAnimal.dto.ProtectDto;
import com.project.dreamingAnimal.entity.ProtectEntity;
import com.project.dreamingAnimal.entity.ShelterEntity;
import com.project.dreamingAnimal.service.ProtectService;
import com.project.dreamingAnimal.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class ProtectController {

    @Autowired
    private ProtectService protectService;

    @Autowired
    private ShelterService shelterService;

    // 리스트
    @GetMapping("/protectL")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,String protectNum,String kind, String gender, String date,Integer isProtect) {
        Page<ProtectDto> list = null;

        if((protectNum != null||  kind != null || gender != null || date != null || isProtect!=null) ){
            list = protectService.listWithImagesAndSerach(pageable,protectNum,kind,  gender, date,isProtect);
        }else{
            list = protectService.listWithImages(pageable);
        }

        // 페이징
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 검색어
        // 받아온 쿼리스트링 값을 모델에 담아서 뷰에 전달
        model.addAttribute("protectNum", protectNum);
        model.addAttribute("date", date);
        model.addAttribute("kind", kind);
        model.addAttribute("isProtect", isProtect);
        model.addAttribute("gender", gender);

        return "protect/list";
    }

    // 권한 설정
    // 글 쓰기 Form
    @Secured("ROLE_SHELTER")
    @GetMapping("/protectW")
    public String writeForm(){
        return "protect/write";
    }

    // 글 작성
    @PostMapping("/protectW")
    public String write(ProtectDto protectDto, MultipartFile file , Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) throws  Exception{

        protectDto.setUsername(principalDetails.getUsername());
        protectService.save(protectDto,file);

        model.addAttribute("message", "보호동물 등록이완료되었습니다.");
        model.addAttribute("searchUrl", "/protectL");

        return "message/message";
    }

    // 글 상세
    @GetMapping("/protectV/{id}")
    public String view(Model model, @PathVariable int id){

        // community에서 글 정보 가져오기
        ProtectEntity protectEntity = protectService.view(id);
        model.addAttribute("board", protectEntity);

        // 보호센터 정보 가져오기
        String Username = protectEntity.getUsername();
        ShelterEntity shelterEntity = shelterService.findByUsername(Username);
        model.addAttribute("shelter", shelterEntity);

        // 저장 파일 가져오기
        List<BoardFileDto> boardFileDtos = protectService.getFileList(id);
        model.addAttribute("boardFileList",boardFileDtos);

        return "protect/view";
    }

    // 글 삭제
    @GetMapping("/protectD/{id}")
    public String delete(Model model,@PathVariable int id){

        // community table에서 해당 글 삭제하기
        // reply tabled에서 해당 글과 관련된 댓글 삭제하기
        // entity만들 때 연관관계에 의해 reply table에서 댓글은 같이 삭제된다.
        // file table에서 해당 게시글에 연관된 파일 삭제하기 file table에서 boardID 가 매개변수로 받은 id와 같으면 삭제하기
        protectService.delete(id);

        // file table에서 해당 게시글에 연관된 파일 실제 저장된 파일을 삭제하기
        List<BoardFileDto> boardFileDtos = protectService.getFileList(id);
        if(boardFileDtos!=null){
            for (BoardFileDto s : boardFileDtos) {
                String srcFileName = null;
                String fileName = s.getStoredFileName();
                String uploadPath = System.getProperty("user.dir")+"/src/main/resources/static/img/protect";
                try {
                    srcFileName = URLDecoder.decode(fileName,"UTF-8");
                    File file = new File(uploadPath + File.separator + srcFileName); // 매개변수 => 파일경로 이다.

                    System.out.println("경로확인 : " + uploadPath + File.separator + srcFileName);
                    boolean result = file.delete(); // true이면 지우기 성공, false면 지우기 실패

                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        }

        model.addAttribute("message", "글이 삭제가 되었습니다.");
        model.addAttribute("searchUrl", "/protectL");

        return "message/message";
    }


    // 글 수정 Form
    @GetMapping("/protectU/{id}")
    public String updateForm(Model model,@PathVariable int id){

        ProtectEntity protectEntity = protectService.updateForm(id);
        model.addAttribute("board",protectEntity);

        if(protectEntity.getFileAttached()==1){
            List<BoardFileDto> boardFileDtos = protectService.getFileList(id);
            model.addAttribute("boardFileList",boardFileDtos);
            model.addAttribute("boardFileList",boardFileDtos);

            // 파일이 잘 가져와졌나 확인용 출력
            for (BoardFileDto item : boardFileDtos) {
                System.out.println(item);
            }
        }

        return "protect/update";
    }

    // 글 수정
    @PostMapping("/protectU")
    public String update(ProtectEntity protectEntity,  List<MultipartFile> files, @RequestParam(required = false) List<Integer> del) throws Exception{

        ProtectEntity protectEntityW =  protectService.view(protectEntity.getId());
        protectService.update(protectEntity,files);

        return "redirect:/protectL";
    }

    // 글 수정 - 보호 종료
    @PostMapping("/protectEnd")
    public String protectEnd(int id,String endReason){
        ProtectEntity protectEntity = protectService.view(id);

        protectEntity.setEndReason(endReason);
        protectEntity.setIsProtect(1);
        protectService.end(protectEntity);

        return "redirect:/protectL";
    }

}


















