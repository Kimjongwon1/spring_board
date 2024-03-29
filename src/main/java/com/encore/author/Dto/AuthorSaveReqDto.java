package com.encore.author.Dto;

import lombok.Data;

@Data
public class AuthorSaveReqDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
}
