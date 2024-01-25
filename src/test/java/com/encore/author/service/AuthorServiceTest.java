package com.encore.author.service;

import com.encore.author.Domain.Author;
import com.encore.author.Dto.AuthorDetailDto;
import com.encore.author.Dto.AuthorUpdateReqDto;
import com.encore.author.Repository.AuthorRepository;
import com.encore.author.Service.AuthorService;
import com.encore.post.Domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void findAuthorDetailTest() {
        Long authorId = 1L;
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("hello")
                .contents("hello word")
                .build();
        posts.add(post);
        Author author = Author.builder()
                .name("test1")
                .email("test1@naver.com")
                .password("1234")
                .posts(posts)
                .build();
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        AuthorDetailDto authorDetailResDto = authorService.authorDetail(authorId);
        Assertions.assertEquals(author.getName(), authorDetailResDto.getName());
        Assertions.assertEquals(author.getPosts().size(), authorDetailResDto.getPostsCount());
        Assertions.assertEquals("유저", authorDetailResDto.getRole());
    }

    @Test
    void updateTest() {
        Long authorId = 1L;
        Author author = Author.builder()
                .name("test1")
                .email("test1@naver.com")
                .password("1234")
                .build();
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        AuthorUpdateReqDto authorUpdateReqDto = new AuthorUpdateReqDto();
        authorUpdateReqDto.setId(authorId);
        authorUpdateReqDto.setName("test2");
        authorUpdateReqDto.setPassword("4321");
        AuthorUpdateReqDto updatedDto = authorService.authorUpdate(authorUpdateReqDto);
        Assertions.assertEquals("test2", updatedDto.getName());
        Assertions.assertEquals("4321", updatedDto.getPassword());
    }

    @Test
    void findAllTest() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Mockito.when(authorRepository.findAll()).thenReturn(authors);
        Assertions.assertEquals(2, authorService.findAll().size());
    }
}
