package top.sunquest.temp2project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Temp2Project {
	public static void main(String[] args) {
		SpringApplication.run(Temp2Project.class, args);
	}
}
