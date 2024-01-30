package com.encore.post.Service;

import com.encore.author.Domain.Author;
import com.encore.author.Repository.AuthorRepository;
import com.encore.post.Domain.Post;
import com.encore.post.Dto.PostDetailDto;
import com.encore.post.Dto.PostListResDto;
import com.encore.post.Dto.PostSaveReqDto;
import com.encore.post.Dto.PostUpdateReqDto;
import com.encore.post.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void PostSave(PostSaveReqDto postSaveReqDto, String email) throws IllegalArgumentException {
//        Post post = new Post(postSaveReqDto.getTitle()
//                ,postSaveReqDto.getContents());
//        Optional<Author> author = authorRepository.findByEmail(postSaveReqDto.getEmail());
//        Post post = Post.builder()
//                .title(postSaveReqDto.getTitle())
//                .contents(postSaveReqDto.getContents())
//                .author(author.orElse(null))
//                .build();
//
//        // 예약 시간 처리
//        if (postSaveReqDto.getAppointmentTime() != null && !postSaveReqDto.getAppointmentTime().isEmpty()) {
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime appointmentTime = LocalDateTime.parse(postSaveReqDto.getAppointmentTime(), dateTimeFormatter);
//            // 여기에 추가적인 예약 시간 관련 로직을 적용할 수 있습니다.
//            post.setAppointmentTime(appointmentTime);
//        }
//
//        postRepository.save(post);

        //author.authorUpdate("dirtychecking test","1234");
       // authorRepository.save(author);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        email = authentication.getName();
        String appointment = null;
        LocalDateTime appointmentTime=null;
        // 예약 시간 처리
        if (postSaveReqDto.getAppointment() != null && !postSaveReqDto.getAppointmentTime().isEmpty()) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                appointmentTime = LocalDateTime.parse(postSaveReqDto.getAppointmentTime(), dateTimeFormatter);
                if(appointmentTime.isBefore(LocalDateTime.now())){
                    throw new IllegalArgumentException("시간설정 오류");
                }
            appointment = "Y";
        }

        Optional<Author> author = authorRepository.findByEmail(email);
        Post post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .author(author.orElse(null))
                .appointment(appointment)
                .appointmentTime(appointmentTime)
                .build();

        postRepository.save(post);
    }


//    Dirtychecking Test
//    public void PostSave(PostSaveReqDto postSaveReqDto) {
//        Optional<Author> optionalAuthor = authorRepository.findByEmail(postSaveReqDto.getEmail());
//        if (optionalAuthor.isPresent()) {
//            Author author = optionalAuthor.get();
//
//            // Author 정보 업데이트
//            author.authorUpdate("dirtychecking test", "1234");
//
//            // Post 객체 생성
//            Post post = Post.builder()
//                    .title(postSaveReqDto.getTitle())
//                    .contents(postSaveReqDto.getContents())
//                    .author(author)
//                    .build();
//
//            // Post 저장
//            postRepository.save(post);
//        } else {
//            // 적절한 예외 처리 또는 로직 구현
//            throw new IllegalArgumentException("작성자를 찾을 수 없습니다.");
//        }
//    }
    public List<PostListResDto> postList(Pageable pageable){
        List<Post> postList  = postRepository.findAllFetchJoin();
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for (Post a : postList) {
            PostListResDto postListResDto = new PostListResDto(a.getId(),
                    a.getTitle(),
                    a.getAuthor() == null?"익명유저":a.getAuthor().getEmail());
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
    public Page<PostListResDto> postListPaging(Pageable pageable) {

        // 예약된 게시물 중 예약 시간이 현재 시간 이전이거나 "N" 또는 null인 게시물을 가져옴
        Page<Post> post = postRepository.findByAppointment(null, pageable);

        Page<PostListResDto> postListResDtos = post.map(
                p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명유저" : p.getAuthor().getName())
        );
        return postListResDtos;
    }


    public PostDetailDto postDetail(Long id) throws EntityNotFoundException {
        return postRepository.findById(id)
                .map(post -> {
                    return new PostDetailDto(
                            post.getId(),
                            post.getTitle(),
                            post.getContents(),
                            post.getCreatedTime(),
                            "Y".equals(post.getAppointment()) ? "예약됨" : "예약되지 않음"
                            ); // Role 객체를 전달
                })
                .orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다"));
    }
    public void postdelete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 저자를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    public List<PostListResDto> searchPosts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // 검색어를 공백으로 분리
        String[] keywords = query.trim().split("\\s+");

        // 각 키워드가 포함된 게시글 검색
        return postRepository.findAll().stream()
                .filter(post -> post != null && Arrays.stream(keywords)
                        .allMatch(keyword ->
                                (post.getTitle() != null && post.getTitle().contains(keyword)) ||
                                        (post.getContents() != null && post.getContents().contains(keyword))))
                .map(post -> new PostListResDto(post.getId(), post.getTitle(), post.getAuthor() != null ? post.getAuthor().getEmail() : "익명유저"))
                .collect(Collectors.toList());
    }

}
