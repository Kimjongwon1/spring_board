package com.encore.post.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime created_time;

    public PostDetailDto(Long id, String title, String contents, LocalDateTime createdTime) {
        this.id =id;
        this.title = title;
        this.contents = contents;
        this.created_time = createdTime;
    }
}
