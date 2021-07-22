package winnow.ak.study.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author: Winyu
 * @Date: 2021/6/8
 */
@Service
public  class BeanLoadSequence implements BeanPostProcessor, BeanFactoryAware, ApplicationContextAware, SmartInitializingSingleton, InitializingBean, DisposableBean {

    // 顺序 @Scope("prototype") construct -> beanFactory -> applicationContext -> init > postProcessBefore -> postProcessAfter
    // 顺序 construct -> beanFactory -> applicationContext ->PostConstruct -> afterPropertiesSet-> init > postProcessBefore -> postProcessAfter -> afterSingletonsInstantiated
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("-------------postProcessBefore-------------1");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("-------------postProcessAfter-------------2");
        return bean;
    }

    public void init(){
        System.out.println("-------------init-------------");
    }

   public BeanLoadSequence(){
       System.out.println("-------------construct-------------3");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("-------------PostConstruct-------------4");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("-------------beanFactory--------5");
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("-------------afterSingletonsInstantiated-----------6");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("-------------applicationContext---------7");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("-------------destroy-----------8");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-------------afterPropertiesSet-----------9");
    }
}
