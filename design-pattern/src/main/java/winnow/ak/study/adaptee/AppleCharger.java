package winnow.ak.study.adaptee;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class AppleCharger implements TypeCInterface {
    @Override
    public void chargeWithTypeC() {
        System.out.println("使用TypeC型号的充电器充电...");
    }
}
