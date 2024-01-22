package com.encore.author.Service;

import com.encore.author.Domain.Author;
import com.encore.author.Dto.AuthorDetailDto;
import com.encore.author.Dto.AuthorListResDto;
import com.encore.author.Dto.AuthorSaveReqDto;
import com.encore.author.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @Transactional
    public void AuthrSave(AuthorSaveReqDto authorSaveReqDto) throws IllegalArgumentException {
        Author author = new Author(authorSaveReqDto.getName()
                ,authorSaveReqDto.getEmail()
                ,authorSaveReqDto.getPassword());
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
                .map(author -> new AuthorDetailDto(
                        author.getId(),
                        author.getName(),
                        author.getEmail(),
                        author.getPassword(),
                        author.getCreated_time()))
                .orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다"));
    }

}
