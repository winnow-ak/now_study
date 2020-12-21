package winnow.ak.study.proxy.processor;

/**
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public interface SmsService {
    void send(String phoneNumber,String txt);
}
