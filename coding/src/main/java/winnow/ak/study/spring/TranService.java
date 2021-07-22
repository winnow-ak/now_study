package winnow.ak.study.spring;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Winyu
 * @Date: 2021/6/19
 */
@Service
public class TranService {

    @Autowired
    private TranscationalB transcationalB;

    List<String> list = Lists.newArrayList();

    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA() {
        list.add("A");
        transcationalB.B(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void methodB() {
        list.add("b");
        try {
            int a =  1/0;
        }catch (Exception e){
            System.out.println("异常了");
        }
        this.methodA();
    }


    public void methodC() {
        this.methodB();
        list.add("C");
        list.forEach(l->{
            System.out.println(l);
        });
    }
}
