package com.encore.author.Controller;

import com.encore.author.Service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//slf4j어노테이션을 통해 쉽게 logback 로그라이브러리 사용가능
@Slf4j
public class TestController {
   // private static final Logger log = LoggerFactory.getLogger(LogTestController.class);
    //Slf4j 어노테이션없이 직접 라이브러리 import하여 로거 생성가능

    @Autowired
    private AuthorService authorService;

    @GetMapping("log/test1")
    public String TestMethod(){
        log.debug("디버그 로그");
        log.info("info 로그");
        log.error("디버그 로그");
        return "ok";
    }

    @GetMapping("exception/test1/{id}")
    public String exceptionTestMethod(@PathVariable Long id){
        authorService.findbyId(id);
        return "ok";
    }
}
