package winnow.ak.study.state;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class SuccessState implements StatePattern {

    @Override
    public void returnState() {
        System.out.println("success do action ...");
    }


}
