package com.encore.post.Controller;

import com.encore.post.Dto.PostDetailDto;
import com.encore.post.Dto.PostListResDto;
import com.encore.post.Dto.PostSaveReqDto;
import com.encore.post.Dto.PostUpdateReqDto;
import com.encore.post.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/create")
    public String postcreate(){
        return "/post/post-create";
    }

    @PostMapping("/create")
    public String createpost(PostSaveReqDto postSaveReqDto) {
        postService.PostSave(postSaveReqDto);
        return "redirect:/post/list"; //url 리다이렉트
    }
    @GetMapping("/list")
    public String postlist(PostListResDto postListResDto, Model model){
      List<PostListResDto> postList = postService.postList();
      model.addAttribute("postlist",postList);
      return "/post/post-list";
    }

    @GetMapping("/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
        PostDetailDto postDetail =  postService.postDetail(id);
        model.addAttribute("post",postDetail);
        return "/post/post-detail";
       // return "author/author-detail";
    }
    @PostMapping("/update")
    public String authorupdate(PostUpdateReqDto postUpdateReqDto){
        postService.updatepost(postUpdateReqDto);
        return "redirect:/post/detail/" + postUpdateReqDto.getId();
    }
    @GetMapping("/delete/{id}")
    public String authordelete(@PathVariable Long id) {
        postService.postdelete(id);
        return "redirect:/post/list";
    }
}
