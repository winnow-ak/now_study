package winnow.ak.study.proxy.aop;

import java.util.Objects;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public class CustomAspect implements IAspect {

    @Override
    public boolean startTransaction(Object... args) {
        Objects.nonNull(args);
        boolean result = true;
        for (Object temp : args) {
            if (Objects.isNull(temp)) {
                result = false;
                break;
            }
        }
        return result;
    }




    @Override
    public void endTrasaction() {
        System.out.println("I get datasource here and end transaction");
    }
}
