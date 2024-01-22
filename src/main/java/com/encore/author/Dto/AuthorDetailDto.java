package com.encore.author.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created_time;

    public AuthorDetailDto(Long id, String name, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_time = createdTime;
    }
}
