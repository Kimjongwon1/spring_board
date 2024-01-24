package com.encore.post.Domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Post {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 3000)
    private String contents;
    @Column(name = "created_time") //name옵션을 통해 DB의 컬럼명 별도 지정
    @CreationTimestamp
    private LocalDateTime created_time;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Post(String title, String contents) {
        this.title =title;
        this.contents = contents;
    }
}
