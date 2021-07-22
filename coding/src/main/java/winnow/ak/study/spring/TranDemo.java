package winnow.ak.study.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Winyu
 * @Date: 2021/6/19
 */
@RestController
public class TranDemo {
    @Autowired
    private TranService tranService;

    @RequestMapping("/test")
    public String test(){
        tranService.methodC();
        return "success";
    }
}
