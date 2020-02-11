package com.rodzyn.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSwagger2
public class HomeWork03Application {

    public static void main(String[] args) {
        SpringApplication.run(HomeWork03Application.class, args);
    }

//    @Bean
//    public Docket getDocket(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }

}
