package com.encore.post.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime created_time;
    private String appointment;

    public PostDetailDto(Long id, String title, String contents, LocalDateTime createdTime, String appointment) {
        this.id =id;
        this.title = title;
        this.contents = contents;
        this.created_time = createdTime;
        this.appointment = appointment;
    }
}
