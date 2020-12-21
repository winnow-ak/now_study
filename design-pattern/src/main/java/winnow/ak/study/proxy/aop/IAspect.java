package winnow.ak.study.proxy.aop;

/**
 * @Author: Winyu
 * @Date: 2020/12/22
 */
public interface IAspect {
    /**
     * 在切点接口方法执行之前执行
     *
     * @param args 切点参数列表
     * @return
     */
    boolean startTransaction(Object... args);

    /**
     * 在切点接口方法执行之后执行
     */
    void endTrasaction();
}
