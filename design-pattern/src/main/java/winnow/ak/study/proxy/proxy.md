### Java 代理模式实现方式，主要有如下五种方法
>1.静态代理，工程师编辑代理类代码，实现代理模式；在编译期就生成了代理类。
 2.基于 JDK 实现动态代理，通过jdk提供的工具方法Proxy.newProxyInstance动态构建全新的代理类(继承Proxy类，并持有InvocationHandler接口引用 )字节码文件并实例化对象返回。(jdk动态代理是由java内部的反射机制来实例化代理对象，并代理的调用委托类方法)
 3.基于CGlib 动态代理模式 基于继承被代理类生成代理子类，不用实现接口。只需要被代理类是非final 类即可。(cglib动态代理底层是借助asm字节码技术
 4.基于 Aspectj 实现动态代理（修改目标类的字节，织入代理的字节，在程序编译的时候 插入动态代理的字节码，不会生成全新的Class ）
 5.基于 instrumentation 实现动态代理（修改目标类的字节码、类装载的时候动态拦截去修改，基于javaagent） -javaagent:spring-instrument-4.3.8.RELEASE.jar （类装载的时候 插入动态代理的字节码，不会生成全新的Class ）

### JDK动态代理与CGLIB实现区别
> 1.JDK动态代理是实现了被代理对象的接口，Cglib是继承了被代理对象。
> 2.JDK和Cglib都是在运行期生成字节码，JDK是直接写Class字节码，Cglib使用ASM框架写Class字节码，Cglib代理实现更复杂，生成代理类比JDK效率低。
> 3.JDK调用代理方法，是通过反射机制调用，Cglib是通过FastClass机制直接调用方法，Cglib执行效率更高。


### CGLIB动态代理
> Cglib是一个强大的，高性能，高质量的代码生成类库。它可以在运行期扩展JAVA类与实现JAVA接口。
其底层实现是通过ASM字节码处理框架来转换字节码并生成新的类。大部分功能实际上是ASM所提供的，
Cglib只是封装了ASM，简化了ASM操作，实现了运行期生成新的class。


### CGLIB原理
>运行时动态的生成一个被代理类的子类（通过ASM字节码处理框架实现），子类重写了被代理类中所有非final的方法。在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势植入横切逻辑。

### Cglib优缺点
>优点：JDK动态代理要求被代理的类必须实现接口，当需要代理的类没有实现接口时Cglib代理是一个很好的选择。另一个优点是Cglib动态代理比使用java反射的JDK动态代理要快
 缺点：对于被代理类中的final方法，无法进行代理，因为子类中无法重写final函数

### spring 如何实现AOP
>1.如果设置了EnableAspectJAutoProxy(proxyTargetClass=true) 一定是cglib代理
>2.如果proxyTargetClass=false，目标对象实现了接口，走JDK代理
>3.如果没有实现接口，走CGLIB代理

#### spring aop 流程
>AnnotationAwareAspectJAutoProxyCreator是AOP核心处理类
 AnnotationAwareAspectJAutoProxyCreator实现了BeanProcessor，其中postProcessAfterInitialization是核心方法。
 核心实现分为2步
 1.getAdvicesAndAdvisorsForBean获取当前bean匹配的增强器 createProxy为当前bean创建代理getAdvicesAndAdvisorsForBean核心逻辑如下
  a. 找所有增强器，也就是所有@Aspect注解的Bean
  b. 找匹配的增强器，也就是根据@Before，@After等注解上的表达式，与当前bean进行匹配，暴露匹配上的。
  c. 对匹配的增强器进行扩展和排序，就是按照@Order或者PriorityOrdered的getOrder的数据值进行排序，越小的越靠前。
 2.createProxy有2种创建方法，JDK代理或CGLIB
  a. 如果设置了proxyTargetClass=true，一定是CGLIB代理
  b. 如果proxyTargetClass=false，目标对象实现了接口，走JDK代理
  c. 如果没有实现接口，走CGLIB代理
