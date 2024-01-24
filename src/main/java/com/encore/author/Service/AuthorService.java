package com.encore.author.Service;

import com.encore.author.Domain.Author;
import com.encore.author.Domain.Role;
import com.encore.author.Dto.AuthorDetailDto;
import com.encore.author.Dto.AuthorListResDto;
import com.encore.author.Dto.AuthorSaveReqDto;
import com.encore.author.Dto.AuthorUpdateReqDto;
import com.encore.author.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @Transactional
    public void AuthrSave(AuthorSaveReqDto authorSaveReqDto) throws IllegalArgumentException {
        Role role = null;
        if(authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")){
            role = Role.USER;
        }else {
            role = Role.ADMIN;
        }
//        일반생성자방식
//        Author author = new Author(authorSaveReqDto.getName()
//                ,authorSaveReqDto.getEmail()
//                ,authorSaveReqDto.getPassword()
//                ,role);
        Author author = Author.builder().name(authorSaveReqDto.getName())
                .email(authorSaveReqDto.getEmail())
                .name(authorSaveReqDto.getName())
                .password(authorSaveReqDto.getPassword())
                .build();

//        cascade.persist 테스트
//        부모테이블을 통해 자식테이블에 객체를 동시에 생성
//        List<Post> posts =new ArrayList<>();
//        Post post = Post.builder()
//                    .title("안녕하세요 " + author.getName()+ " 입니다.")
//                    .contents("cascade 테스트중입니다.")
//                    .author(author)
//                    .build();
//        posts.add(post);
//        author.setPosts(posts);

        authorRepository.save(author);

    }

    public List<AuthorListResDto> authorList(){
        List<Author> authorList  = authorRepository.findAll();
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        for (Author a : authorList) {
            AuthorListResDto authorListResDto = new AuthorListResDto(a.getId(),a.getName(),a.getEmail());
            authorListResDtos.add(authorListResDto);
        }
        return authorListResDtos;
    }

    public AuthorDetailDto authorDetail(Long id) throws EntityNotFoundException {
        return authorRepository.findById(id)
                .map(author -> {
                    Role role;
                    if (author.getRole() == null || author.getRole().equals("user")) {
                        role = Role.USER;
                    } else {
                        role = Role.ADMIN;
                    }
                    return new AuthorDetailDto(
                            author.getId(),
                            author.getName(),
                            author.getEmail(),
                            author.getPassword(),
                            author.getCreated_time(),
                            role,// Role 객체를 전달
                            author.getPostsCount());
                })
                .orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다"));
    }
    public AuthorUpdateReqDto authorUpdate(AuthorUpdateReqDto authorUpdateReqDto) throws EntityNotFoundException {
        Author author = authorRepository.findById(authorUpdateReqDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 사용자로부터 받은 새 정보로 Author 객체 업데이트
        if (authorUpdateReqDto.getName() != null) {
            author.setName(authorUpdateReqDto.getName());
        }
        if (authorUpdateReqDto.getPassword() != null) {
            author.setPassword(authorUpdateReqDto.getPassword());
        }

        // 변경된 Author 객체 저장
        authorRepository.save(author);

        // 변경된 정보를 반영한 새로운 DTO 생성 및 반환
        AuthorUpdateReqDto updatedDto = new AuthorUpdateReqDto();
        updatedDto.setId(author.getId());
        updatedDto.setName(author.getName());
        updatedDto.setPassword(author.getPassword());


        //명시적으로 save를 하지않더라도 jpa의 영속성컨텍스트를 통해, 객체에 변경이 감지(더티체킹)되면, 트랜잭션이 완료되는 시점에 save동작

        return updatedDto;
    }


    public void authorDelete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 저자를 찾을 수 없습니다."));
        authorRepository.delete(author);
    }


}
