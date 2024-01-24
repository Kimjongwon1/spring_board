package com.encore.author.Dto;

import lombok.Data;

@Data
public class AuthorUpdateReqDto {
    private Long id;
    private String name;
    private String password;
}
