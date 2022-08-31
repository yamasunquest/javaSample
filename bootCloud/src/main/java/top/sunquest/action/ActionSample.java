package top.sunquest.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "top.sunquest.action.*")
public class ActionSample {

    public static void main(String args[]) {
        SpringApplication.run(ActionSample.class, args);
    }
}