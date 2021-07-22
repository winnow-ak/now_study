package winnow.ak.study;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import winnow.ak.study.spring.BeanLoadSequence;

/**
 * @Author: Winyu
 * @Date: 2021/5/15
 */
@SpringBootApplication
@EnableTransactionManagement
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class,args);
    }
}

// 测试初始化
@Configuration
class Config{
    @Bean(initMethod = "init")
    public BeanLoadSequence loadSequence(){
        return new BeanLoadSequence();
    }

}
