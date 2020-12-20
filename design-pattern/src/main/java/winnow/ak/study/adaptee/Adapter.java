package winnow.ak.study.adaptee;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class Adapter implements TypeCInterface {

    private UsbInterface usbInterface;

    public Adapter() {
    }

    public Adapter(UsbInterface usbInterface) {
        this.usbInterface = usbInterface;
    }

    @Override
    public void chargeWithTypeC() {
        usbInterface.chargeWithMicroUsb();
    }

    public UsbInterface getUsbInterface() {
        return usbInterface;
    }

    public void setUsbInterface(UsbInterface usbInterface) {
        this.usbInterface = usbInterface;
    }
}
