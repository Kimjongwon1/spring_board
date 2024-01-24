package com.encore.author.Domain;

import com.encore.post.Domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Author {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @Column(name = "created_time") //name옵션을 통해 DB의 컬럼명 별도 지정
    @CreationTimestamp
    private LocalDateTime created_time;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Builder
    public Author(String name, String email, String password, Role role, List<Post> posts) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.posts = posts;
    }

    public int getPostsCount() {
        return posts != null ? posts.size() : 0;
    }
}
