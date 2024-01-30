package com.encore.post.Controller;

import com.encore.post.Dto.PostDetailDto;
import com.encore.post.Dto.PostListResDto;
import com.encore.post.Dto.PostSaveReqDto;
import com.encore.post.Dto.PostUpdateReqDto;
import com.encore.post.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String createpost(PostSaveReqDto postSaveReqDto, HttpSession req) {
//       , HttpServletRequest req를 매개변수로 주입한 뒤에
        // HttpSession session = req.getSession(); 로 꺼내고
        // session.getAttribute("email");
        postService.PostSave(postSaveReqDto,req.getAttribute("email").toString());
        return "redirect:/post/list"; //url 리다이렉트
    }
    @GetMapping("/list")
    public String postList(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<PostListResDto> postList = postService.postListPaging(pageable); // postService를 통해 페이지 정보를 가져옴
        model.addAttribute("postList", postList);
        return "post/post-list"; // Thymeleaf 템플릿 이름
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

    @GetMapping("/search")
    public String searchPosts(@RequestParam(value = "query", required = false) String query, Model model) {
        List<PostListResDto> searchResults = postService.searchPosts(query);
        model.addAttribute("postList", searchResults);
        model.addAttribute("searchQuery", query);
        return "post/post-list"; // 검색 결과를 post-list 페이지로 보냄
    }
}
