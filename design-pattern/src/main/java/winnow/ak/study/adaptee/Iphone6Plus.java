package winnow.ak.study.adaptee;

/**
 * @Author: Winyu
 * @Date: 2020/12/1
 */
public class Iphone6Plus {
    private TypeCInterface typeCInterface;

    public  Iphone6Plus(){

    }

    public Iphone6Plus(TypeCInterface typeCInterface) {
        this.typeCInterface = typeCInterface;
    }

    public void charge() {
        System.out.println("开始给我的Iphone6Plus手机充电...");
        typeCInterface.chargeWithTypeC();
        System.out.println("结束给我的Iphone6Plus手机充电...");
    }

    public TypeCInterface getTypeCInterface() {
        return typeCInterface;
    }

    public void setTypeCInterface(TypeCInterface typeCInterface) {
        this.typeCInterface = typeCInterface;
    }
}
