package winnow.ak.study.state;


/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class FailedState implements StatePattern {

    public void returnState() {
        System.out.println(" failed do action ...");
    }


}
