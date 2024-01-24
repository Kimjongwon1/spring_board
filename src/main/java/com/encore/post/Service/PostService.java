package com.encore.post.Service;

import com.encore.post.Domain.Post;
import com.encore.post.Dto.PostDetailDto;
import com.encore.post.Dto.PostListResDto;
import com.encore.post.Dto.PostSaveReqDto;
import com.encore.post.Dto.PostUpdateReqDto;
import com.encore.post.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void PostSave(PostSaveReqDto postSaveReqDto) throws IllegalArgumentException {
//        Post post = new Post(postSaveReqDto.getTitle()
//                ,postSaveReqDto.getContents());
        Post post = Post.builder()
                        .title(postSaveReqDto.getTitle())
                        .contents(postSaveReqDto.getContents())
                        .build();
        postRepository.save(post);
    }
    public List<PostListResDto> postList(){
        List<Post> postList  = postRepository.findAll();
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for (Post a : postList) {
            PostListResDto postListResDto = new PostListResDto(a.getId(),a.getTitle());
            postListResDtos.add(postListResDto);
        }
        return postListResDtos;
    }
    public PostUpdateReqDto updatepost(PostUpdateReqDto postUpdateReqDto){
        Post post = postRepository.findById(postUpdateReqDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 사용자로부터 받은 새 정보로 Author 객체 업데이트
        if (postUpdateReqDto.getTitle() != null) {
            post.setTitle(postUpdateReqDto.getTitle());
        }
        if (postUpdateReqDto.getContents() != null) {
            post.setContents(postUpdateReqDto.getContents());
        }

        // 변경된 Author 객체 저장
        postRepository.save(post);

        // 변경된 정보를 반영한 새로운 DTO 생성 및 반환
        PostUpdateReqDto updatedDto = new PostUpdateReqDto();
        updatedDto.setId(post.getId());
        updatedDto.setTitle(post.getTitle());
        updatedDto.setContents(post.getContents());

        return updatedDto;
    }
    public PostDetailDto postDetail(Long id) throws EntityNotFoundException {
        return postRepository.findById(id)
                .map(post -> {
                    return new PostDetailDto(
                            post.getId(),
                            post.getTitle(),
                            post.getContents(),
                            post.getCreated_time()
                            ); // Role 객체를 전달
                })
                .orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다"));
    }
    public void postdelete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 저자를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

}
