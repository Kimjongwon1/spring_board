package com.encore.post.Dto;

import lombok.Data;

@Data
public class PostSaveReqDto {
    private String title;
    private String contents;
    private String email;
}
