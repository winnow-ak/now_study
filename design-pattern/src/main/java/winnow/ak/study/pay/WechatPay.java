package winnow.ak.study.pay;

import org.springframework.stereotype.Service;

/**
 * @Author: Winyu
 * @Date: 2021/1/18
 */
@PayType(type = "wechat",name = "微信")
@Service
public class WechatPay implements IPay {
    @Override
    public void pay() {
        System.out.println("===微信===");
    }
}
