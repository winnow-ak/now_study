package winnow.ak.study.adaptee;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class DemoAdapter {
    public static void main(String[] args) {
        Iphone6Plus iphone6Plus = new Iphone6Plus(new AppleCharger());
        iphone6Plus.charge();

        System.out.println("==============================");

        XiaoMi8 xiaoMi8 = new XiaoMi8(new AndroidCharger());
        xiaoMi8.charge();

        System.out.println("==============================");

        Adapter adapter  = new Adapter(new AndroidCharger());
        Iphone6Plus newIphone = new Iphone6Plus();
        newIphone.setTypeCInterface(adapter);
        newIphone.charge();
    }

}
