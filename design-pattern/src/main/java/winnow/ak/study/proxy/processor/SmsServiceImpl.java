package winnow.ak.study.proxy.processor;

/**
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public class SmsServiceImpl implements SmsService {

    public SmsServiceImpl() {
        System.out.println("初始化构造函数。。。。。。。。");
    }

    public void init() {
        System.out.println("init method。。。。。。。。");
    }

    @Override
    public void send(String phoneNumber, String txt) {
        System.out.println("手机号：" + phoneNumber + ",收到信息：" + txt);
    }
}
