package winnow.ak.study.state;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class SuccessState implements StatePattern {

    public void returnState() {
        System.out.println("success do action ...");
    }


}
