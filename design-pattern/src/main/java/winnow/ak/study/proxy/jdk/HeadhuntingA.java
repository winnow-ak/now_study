package winnow.ak.study.proxy.jdk;

/**
 * 代理对象
 *
 * @Author: Winyu
 * @Date: 2020/12/21
 */
public class HeadhuntingA implements TargetService {


    @Override
    public void pushResume(String target) {
        System.out.println(this.getClass().getName() + ",猎头A帮" + target + "投了阿里简历");
    }
}
