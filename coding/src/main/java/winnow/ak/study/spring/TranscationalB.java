package winnow.ak.study.spring;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Winyu
 * @Date: 2021/6/19
 */
@Service
public class TranscationalB {


    @Transactional(propagation = Propagation.REQUIRED)
    public List<String> B(List<String> name){
         name.add("B");
         return name;
    }
}
