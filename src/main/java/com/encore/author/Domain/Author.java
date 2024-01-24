package com.encore.author.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Getter
@Setter
//@AllArgsConstructor
//위와같이 모든 매개변수가 있는 생성자를 생성하는 어노테이션과 BUilder를 클래스에 붙여
//모든 매개변수가 있는 생성자위에 Builder어노테이션을 붙인것과 같은 효과가 있음
public class Author {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
// builder 어노테이션을 통해 빌더패턴으로 객체생성
   // @Builder

    public Author(String name, String email, String password,Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role  = role;
    }

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Setter
    @Column(name = "created_time") //name옵션을 통해 DB의 컬럼명 별도 지정
    @CreationTimestamp
    private LocalDateTime created_time;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

}
