package winnow.ak.study.state;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class DemoState {
    public static void main(String[] args) {
        Context context = new Context( new SuccessState());
        for (int i = 0; i < 12; i++) {
            context.changeStatus();
        }
    }
}
