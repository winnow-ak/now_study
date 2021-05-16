##队列
 ### BlockingQueue
   1. 有界队列
   2. 无界队列

   核心方法

| - | 抛异常 | 特殊值| 阻塞| 超时|
| ---| ---| ---| ---| ---|
| 添加| add | offer| put | offer|
| 删除| remove | poll | take | poll|
| 检查| element | peek | 不支持 | 不支持|

主要方法
```java
//将给定元素设置到队列中，如果设置成功返回true, 否则返回false。如果是往限定了长度的队列中设置值，推荐使用offer()方法。
boolean add(E e);

//将元素设置到队列中，设置成功返回true, 否则返回false. e的值不能为空，否则抛出空指针异常。
boolean offer(E e);
    
//将元素设置到队列中，如果队列中没有多余的空间，该方法会一直阻塞，直到队列中有多余的空间。
void put(E e) throws InterruptedException;

//将给定元素在给定的时间内设置到队列中，如果设置成功返回true, 否则返回false.
boolean offer(E e, long timeout, TimeUnit unit)
throws InterruptedException;

//从队列中获取值，如果队列中没有值，线程会一直阻塞，直到队列中有值，并且该方法取得了该值。
E take() throws InterruptedException;

//在给定的时间里，从队列中获取值，时间到了直接调用普通的poll方法，为null则直接返回null。
E poll(long timeout, TimeUnit unit)
    throws InterruptedException;

//获取队列中剩余的空间。
int remainingCapacity();

//从队列中移除指定的值。
boolean remove(Object o);

//判断队列中是否拥有该值。
public boolean contains(Object o);

//将队列中值，全部移除，并发设置到给定的集合中。
int drainTo(Collection<? super E> c);

//指定最多数量限制将队列中值，全部移除，并发设置到给定的集合中。
int drainTo(Collection<? super E> c, int maxElements);
```


 

  ### 有界队列 
- ArrayBlockingQueue : 
    * 是一个**数组**实现的有界阻塞队列，先进先出排序，支持公平锁和非公平锁，
- SynchronousQueue   
    * 一个不存储元素的阻塞队列，每一个put操作必须等待take操作，否则不能添加元素。支持公平锁和非公平锁。
 ### 无界队列
- LinkedBlockingQueue 
    * 基于**链表**的阻塞队列，具体大小则表示有界队列，不指定大小，默认指定Integer.MAX_VALUE，此队列按照先进先出的顺序进行排序。
   
- DelayQueue
    * 已个实现PriorityBlockingQueue实现延迟获取的无界队列
- PriorityBlockingQueue
    * 一个支持线程优先级排序的无界队列，默认自然序进行排序，也可以自定义实现compareTo()方法来指定元素排序规则，不能保证同优先级元素的顺序。
- LinkedBlockingDeque
    * 一个由链表结构组成的双向阻塞队列。队列头部和尾部都可以添加和移除元素，多线程并发时，可以将锁的竞争最多降到一半。
    
    