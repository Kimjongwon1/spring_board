package com.encore.post.Service;

import com.encore.post.Domain.Post;
import com.encore.post.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
@Component
public class Postscheduler {
    private final PostRepository postRepository;
    @Autowired
    public Postscheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
//    초 분 시간 일 월 요일 형태로 스케쥴링 설정
//    * : 매 초(본/시) 등을 의미한다.
//    0/특정숫자 : 특정숫자마다.
//    특정 숫자 : 특정숫자의 초(분/시) 등을 의미
//    ex)0 * * * *: 매일 0분 0초에 스케쥴링 시작
//    ex)0 0/1 * * * * : 매일 1분마다 0초에 스케쥴링 시작:
//    ex)0/1 * * * * * :매초마다
//    ex)0 0 11 * * * : 매일 11시에 스케쥴링
    //@Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void postSchedule(){
    System.out.println("==스케쥴러시작===");

    // 예약 상태가 'Y'이고 예약 시간이 현재 시간 이전인 게시물을 가져옴
    Page<Post> posts = postRepository.findByAppointment("Y",Pageable.unpaged());

    for (Post p : posts.getContent()){
        if(p.getAppointmentTime().isBefore(LocalDateTime.now())){
            p.updateAppointment(null);
            postRepository.save(p);
        }
    }

    System.out.println("==스케쥴러 끝==");
}


}
