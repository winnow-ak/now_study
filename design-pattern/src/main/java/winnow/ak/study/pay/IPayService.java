package winnow.ak.study.pay;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Winyu
 * @Date: 2021/1/18
 */
@Service
public class IPayService implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String,IPay> iPayMap = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PayType.class);
        if (beansWithAnnotation !=null){
            iPayMap = new HashMap<>();
            beansWithAnnotation.forEach((k,v)->{
                String type = v.getClass().getAnnotation(PayType.class).type();
                iPayMap.put(type,(IPay) v);
            });
        }
    }




    public void pay(String type) {
        if (iPayMap !=null){
            iPayMap.get(type).pay();
        }
    }

}
