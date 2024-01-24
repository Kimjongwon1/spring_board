package com.encore.post.Dto;

import lombok.Data;

@Data
public class PostUpdateReqDto {
    private Long id;
    private String title;
    private String contents;
}
