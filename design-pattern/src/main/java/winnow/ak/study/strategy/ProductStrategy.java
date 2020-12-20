package winnow.ak.study.strategy;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class ProductStrategy extends Strategy {


    @Override
    void initialize() {
        System.out.println("product initialize");
    }

    @Override
    void startPlay() {
        System.out.println("product startPlay");

    }

    @Override
    void endPlay() {
        System.out.println("product endPlay");
    }
}
