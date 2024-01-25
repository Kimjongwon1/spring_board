package com.encore.author.Controller;

import com.encore.author.Domain.Author;
import com.encore.author.Dto.AuthorDetailDto;
import com.encore.author.Dto.AuthorListResDto;
import com.encore.author.Dto.AuthorSaveReqDto;
import com.encore.author.Dto.AuthorUpdateReqDto;
import com.encore.author.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/create")
    public String authorcreate(){
        return "author/author-create";
    }
    @PostMapping("/create")
    public String AuthorSave(AuthorSaveReqDto authorSaveReqDto){
        authorService.AuthrSave(authorSaveReqDto);
        return "redirect:/author/list";
    }

    @PostMapping("/update")
    public String authorupdate(AuthorUpdateReqDto authorUpdateReqDto){
        authorService.authorUpdate(authorUpdateReqDto);
        return "redirect:/author/detail/" + authorUpdateReqDto.getId();
    }
    @GetMapping("/delete/{id}")
    public String authordelete(@PathVariable Long id) {
        authorService.authorDelete(id);
        return "redirect:/author/list";
    }

    //    @GetMapping("/list")
//    @ResponseBody
//    public List<AuthorListResDto> authorList(){
//     List<AuthorListResDto> authorList = authorService.authorList();
//        return authorList;
//    }
    @GetMapping("/list")
    public String authorList(Model model){
        List<AuthorListResDto> authorList = authorService.authorList();
        model.addAttribute("authorList",authorList);
        return "author/author-list";
    }
    @GetMapping("/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
      AuthorDetailDto authorList =  authorService.authorDetail(id);
      model.addAttribute("author",authorList);
        return "author/author-detail";
    }
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될 경우
//    순환참조 이슈 발생하므로 dto 사용필요
    @GetMapping("/circle/issue/{id}")
    @ResponseBody
    public Author circleIssue(@PathVariable Long id){
        return authorService.findbyId(id);
    }
    @GetMapping("/circle/dto/{id}")
    @ResponseBody
    public AuthorDetailDto circleIssuetest(@PathVariable Long id){
        return authorService.authorDetail(id);
    }
}
