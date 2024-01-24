package com.encore.post.Dto;

import lombok.Data;

@Data
public class PostListResDto {
    private Long id;
    private String title;
    private String author_email;

    public PostListResDto(Long id, String title, String email) {
        this.id = id;
        this.title = title;
        this.author_email =email;
    }
}
