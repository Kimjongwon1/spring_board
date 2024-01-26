package com.encore.post.Domain;

import com.encore.author.Domain.Author;
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
    //author_id 는 DB의 컬럼명이다, 별 다른 옵션이 없을시 author의 pk에 fk가 설정
//    post객체 입장에서는 한사람이 여러개 글을 쓸 수 있으므로 N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
//    @JoinColumn(nullable=flase, name="author_email",referencedColumnName="email")
    private Author author;
    @Column(name = "created_time") //name옵션을 통해 DB의 컬럼명 별도 지정
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    private LocalDateTime appointmentTime;
    private String appointment;


   public void updateAppointment(String appointment){
        this.appointment = appointment;
    }
}
