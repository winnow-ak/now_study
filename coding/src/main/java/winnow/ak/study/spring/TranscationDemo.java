package winnow.ak.study.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Winyu
 * @Date: 2021/5/22
 */
@RestController
public class TranscationDemo {


    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA(){

    }

}
