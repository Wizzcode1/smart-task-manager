package com.example.smarttaskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmartTaskManagerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SmartTaskManagerApplication.class, args);
    }

}
