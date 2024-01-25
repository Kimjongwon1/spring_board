package com.encore.author.Repository;

import com.encore.author.Domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    //findBy컬럼명 (findBy컬럼명A&B도 가능함)의 규칙으로 자동으로 where조건문을 사용한 메소드 생성
   Optional<Author> findByEmail(String email);

    //Object findAlll();
}
