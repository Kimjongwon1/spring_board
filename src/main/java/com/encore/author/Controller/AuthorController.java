package com.encore.author.Controller;

import com.encore.author.Dto.AuthorDetailDto;
import com.encore.author.Dto.AuthorListResDto;
import com.encore.author.Dto.AuthorSaveReqDto;
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
    @PostMapping("/save")
    @ResponseBody
    public String AuthorSave(AuthorSaveReqDto authorSaveReqDto){
        authorService.AuthrSave(authorSaveReqDto);
        return "ok";
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
