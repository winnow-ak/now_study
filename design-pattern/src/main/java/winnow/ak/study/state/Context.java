package winnow.ak.study.state;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class Context {

    public StatePattern status;

    public Context(StatePattern status) {
        this.status = status;
    }

    private int capacity = 0;

    public void changeStatus() {
        if (capacity == 0) {
            this.status = new FailedState();
        }
        if (capacity > 0 && capacity <= 4) {
            this.status = new FailedState();
        }
        if (capacity == 5) {
            this.status = new SuccessState();
        }
        capacity ++ ;
        status.returnState();
    }
}
