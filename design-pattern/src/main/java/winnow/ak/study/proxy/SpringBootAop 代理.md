### Spring Boot 2.x AOP 默认动态代理方式是？
运行环境:
>SpringBoot 2.2.0.RELEASE 版本，内置 Spring Framework 版本为 5.2.0.RELEASE 版本。同时添加 spring-boot-starter-aop 依赖，自动装配 Spring AOP。

结果表示
> SpringBoot2.x AOP 默认使用Cglib代理，虽然EnableAspectJAutoProxy 默认使用JDK代理，通过proxyTargetClass 设置true 还是Cglib代理
#### Spring Framework 5.x
 整理一下思路
>1.有人说 Spring5 开始 AOP 默认使用 CGLIB 了
2.Spring Framework 5.x 文档和 @EnableAspectJAutoProxy源码注释都说了默认是使用 JDK 动态代理
3.程序运行结果说明，即使继承了接口，设置proxyTargetClass为false，程序依旧使用 CGLIB 代理

**改用Spring 启动程序非Spring Boot 启动**
整理一下思路
>1.Spring5 AOP 默认依旧使用 JDK 动态代理，官方文档和源码注释没有错。
2.SpringBoot 2.x 版本中，AOP 默认使用 cglib，且无法通过proxyTargetClass进行修改。
3.那是不是 SpringBoot 2.x 版本做了一些改动呢？

再探 SpringBoot 2.x
>通过断点调试可以看到，和 AOP 相关的自动配置是通过org.springframework.boot.autoconfigure.aop.AopAutoConfiguration来进行配置的。 
>matchIfMissing = true 标识默认使用Cglib代理

```java
@Configuration
@ConditionalOnClass({ EnableAspectJAutoProxy.class, Aspect.class, Advice.class,
		AnnotatedElement.class })
@ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
public class AopAutoConfiguration {

	@Configuration
	@EnableAspectJAutoProxy(proxyTargetClass = false)
	@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false", matchIfMissing = false)
	public static class JdkDynamicAutoProxyConfiguration {

	}

	@Configuration
	@EnableAspectJAutoProxy(proxyTargetClass = true)
	@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true", matchIfMissing = true)
	public static class CglibAutoProxyConfiguration {

	}
}
```
#### SpringBoot 2.x 中如何修改 AOP 实现
在application.properties文件中通过spring.aop.proxy-target-class来配置
spring.aop.proxy-target-class=false

在 SpringBoot 1.5.x 版本中，默认还是使用 JDK 动态代理的。
#### 为什么 SpringBoot 2.x 为何默认使用 Cglib
>因为 JDK 动态代理是基于接口的，代理生成的对象只能赋值给接口变量。
 而 CGLIB 就不存在这个问题。因为 CGLIB 是通过生成子类来实现的，代理对象无论是赋值给接口还是实现类这两者都是代理对象的父类。
 SpringBoot 正是出于这种考虑，于是在 2.x 版本中，将 AOP 默认实现改为了 CGLIB。

#### 总结
>Spring 5.x 中 AOP 默认依旧使用 JDK 动态代理。
 SpringBoot 2.x 开始，为了解决使用 JDK 动态代理可能导致的类型转化异常而默认使用 CGLIB。
 在 SpringBoot 2.x 中，如果需要默认使用 JDK 动态代理可以通过配置项spring.aop.proxy-target-class=false来进行修改，proxyTargetClass配置已无效。

参考链接：https://www.cnblogs.com/coderxiaohei/p/11758239.html