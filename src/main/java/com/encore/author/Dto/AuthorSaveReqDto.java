package com.encore.author.Dto;

import lombok.Data;

@Data
public class AuthorSaveReqDto {
    private int id;
    private String name;
    private String email;
    private String password;
}
