package winnow.ak.study.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Winyu
 * @Date: 2021/6/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTest {

    @Autowired
    private TranService tranService;

    @Test
    public void test(){
        tranService.methodC();
    }
}
