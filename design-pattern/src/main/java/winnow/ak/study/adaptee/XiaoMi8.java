package winnow.ak.study.adaptee;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class XiaoMi8 {
    private UsbInterface usbInterface;

    public XiaoMi8(UsbInterface usbInterface) {
        this.usbInterface = usbInterface;
    }
    public XiaoMi8(){}

    public void charge(){
        System.out.println("开始给我的小米8手机充电...");
        usbInterface.chargeWithMicroUsb();
        System.out.println("开始给我的小米8手机充电...");
    }
}
