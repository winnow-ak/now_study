package winnow.ak.study.strategy;

/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public abstract class Strategy {

    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    //模板
    public final void play() {

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }

}
