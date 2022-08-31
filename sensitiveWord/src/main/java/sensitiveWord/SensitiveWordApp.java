package sensitiveWord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sensitiveWord.dao.LoadResources;

import java.io.FileNotFoundException;

/**
 * 本程序提供了，敏感词维护和过滤能力
 * 敏感词维护非线程安全，因此请勿在多端情况下调用
 * 敏感词过滤线程安全，可以支持多端同时调用
 * <p>
 */
@SpringBootApplication
@ComponentScan(value = "sensitiveWord.*")
public class SensitiveWordApp {
    public static void main(String args[]) throws FileNotFoundException {
        SpringApplication.run(SensitiveWordApp.class, args);
    }
}