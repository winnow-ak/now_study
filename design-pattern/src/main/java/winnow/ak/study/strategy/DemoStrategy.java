package winnow.ak.study.strategy;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class DemoStrategy {
    public static void main(String[] args) {
        Strategy strategy = new ProductStrategy();
        strategy.play();
    }
}

