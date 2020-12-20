package winnow.ak.study.adaptee;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class AndroidCharger implements UsbInterface {
    @Override
    public void chargeWithMicroUsb() {
        System.out.println("使用MicroUsb型号的充电器充电...");
    }
}
