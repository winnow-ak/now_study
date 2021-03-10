package winnow.ak.study.pay;

import org.springframework.stereotype.Service;

/**
 * @Author: Winyu
 * @Date: 2021/1/18
 */
@PayType(type = "ali",name="支付宝支付")
@Service
public class AliPay  implements IPay{
    @Override
    public void pay() {
        System.out.println("=====ali pay ===");
    }
}
