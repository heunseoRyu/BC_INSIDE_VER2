package com.gal.bc_inside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BcInsideVer2Application {

    public static void main(String[] args) {
        SpringApplication.run(BcInsideVer2Application.class, args);
    }

}
