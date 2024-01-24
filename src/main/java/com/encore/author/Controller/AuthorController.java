package com.encore.author.Controller;

import com.encore.author.Dto.*;
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

}
