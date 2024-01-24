package com.encore.post.Dto;

import lombok.Data;

@Data
public class PostListResDto {
    private Long id;
    private String title;

    public PostListResDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
