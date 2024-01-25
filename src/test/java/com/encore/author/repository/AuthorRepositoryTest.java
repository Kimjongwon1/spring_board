package com.encore.author.repository;

import com.encore.author.Domain.Author;
import com.encore.author.Domain.Role;
import com.encore.author.Repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 DB 원상 복구
//모든 스프링빈을 생성하지않고, DB테스트 특화 어노테이션
//@DataJpaTest
//SpringBootTest 어노테이션은 자동롤백 안됨,별도 롤백 코드 또는 어노테이션 필요
//실제 스프링 실행과 동일하게 스프링빈생성 및 주입

//@SpringBootTest
//@Transactional
@DataJpaTest
//(replace = AutoConfigureTestDatabase.Replace.Any 가 default, 뜻 : h2DB가 Default, spring 내장 인메모리
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void AuthorsaveTest(){
//        객체를 만들고 save하고 , 다시 조회해서 방금 만든 객체로 비교
//        준비(prepare, given)
        Author author = Author.builder()
                .email("14test3@naver0.com")
                .name("1test2")
                .password("12345777")
                .role(Role.ADMIN)
                .build();
//        실행(excute, when)
        authorRepository.save(author);
        Author authorDB  = authorRepository.findByEmail("14test3@naver0.com").orElse(null);
//        검증(then)
//        Assertion클래스의 기능을 통해 오류의 원인 파악,  null
       Assertions.assertEquals(author.getEmail(), authorDB.getEmail());
    }
}
