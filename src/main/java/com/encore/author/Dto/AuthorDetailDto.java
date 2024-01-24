package com.encore.author.Dto;

import com.encore.author.Domain.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created_time;
    private String role;

    public AuthorDetailDto(Long id, String name, String email, String password, LocalDateTime createdTime, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_time = createdTime;
        this.setRole(role); // setRole 메서드를 호출하여 role을 설정
    }

    public void setRole(Role role) {
        // Role을 String으로 변환하여 설정
        if (role == Role.USER) {
            this.role = "유저";
        } else if (role == Role.ADMIN) {
            this.role = "관리자";
        } else {
            this.role = null; // 또는 기본값 설정
        }
    }
}
