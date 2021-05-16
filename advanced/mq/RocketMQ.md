
### 消息存储
RocketMQ的消息存储主要分为：CommitLog、ConsumeQueue和IndexFile

1. CommitLog
用于存储Producer端写入的消息元数据及消息主体内容，内容不定长，不区分topic
每个文件大小固定，默认为1G，文章名固定为20字节，为起始的偏移量，如：00000000000000000000为第一个文件，00000000001073741824为第二个文件
消息主要是顺序写入文件，当文件满了，写入下一个文件

2. ConsumeQueue
由于CommitLog的存储是不区分Topic的，即所有的Topic
的消息都存储在同一个CommitLog中，而消息的消费又是基于Topic的。为了提高消息消费的性能，避免低效地遍历commitlog文件来检索topic消息，便引入了ConsumeQueue的设计
ConsumeQueue本质上是commitlog基于topic的索引文件，保存了指定Topic下的队列消息在CommitLog中的起始物理偏移量offset，消息大小size和消息Tag的HashCode值。

3. IndexFile
提供了一种可以通过key或时间区间来查询消息的方法（key的结构为：topic + “#” + UNIQ_KEY或topic + “#” + KEY）
IndexFile的底层存储设计为在文件系统中实现HashMap结构，其存储位置：$HOME\store\index${fileName}，fileName是创建时的时间戳


##### 6、RocketMQ Broker中的消息被消费后会立即删除吗？
不会，每条消息都会持久化到CommitLog中，每个Consumer连接到Broker后会维持消费进度信息，当有消息消费后只是当前Consumer的消费进度（CommitLog的offset）更新了。

追问：那么消息会堆积吗？什么时候清理过期消息？
4.6版本默认48小时后会删除不再使用的CommitLog文件

检查这个文件最后访问时间
判断是否大于过期时间
指定时间删除，默认凌晨4点



#### 7、RocketMQ消费模式有几种？
消费模型由Consumer决定，消费维度为Topic。

集群消费
1.一条消息只会被同Group中的一个Consumer消费
2.多个Group同时消费一个Topic时，每个Group都会有一个Consumer消费到数据

广播消费
消息将对一 个Consumer Group 下的各个 Consumer 实例都消费一遍。即即使这些 Consumer 属于同一个Consumer Group ，消息也会被 Consumer Group 中的每个 Consumer 都消费一次。

#### 8、消费消息是push还是pull？
RocketMQ没有真正意义的push，都是pull，虽然有push类，但实际底层实现采用的是长轮询机制，即拉取方式

broker端属性 longPollingEnable 标记是否开启长轮询。默认开启

源码如下：

// {@link org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl#pullMessage()}
// 看到没，这是一只披着羊皮的狼，名字叫PushConsumerImpl，实际干的确是pull的活。

// 拉取消息，结果放到pullCallback里
this.pullAPIWrapper.pullKernelImpl(pullCallback);
追问：为什么要主动拉取消息而不使用事件监听方式？
事件驱动方式是建立好长连接，由事件（发送数据）的方式来实时推送。

如果broker主动推送消息的话有可能push速度快，消费速度慢的情况，那么就会造成消息在consumer端堆积过多，同时又不能被其他consumer消费的情况。而pull的方式可以根据当前自身情况来pull，不会造成过多的压力而造成瓶颈。所以采取了pull的方式。

#### 9、broker如何处理拉取请求的？
Consumer首次请求Broker

Broker中是否有符合条件的消息
有 ->
响应Consumer
等待下次Consumer的请求
没有
DefaultMessageStore#ReputMessageService#run方法
PullRequestHoldService 来Hold连接，每个5s执行一次检查pullRequestTable有没有消息，有的话立即推送
每隔1ms检查commitLog中是否有新消息，有的话写入到pullRequestTable
当有新消息的时候返回请求
挂起consumer的请求，即不断开连接，也不返回数据
使用consumer的offset，
#### 10、RocketMQ如何做负载均衡？
##### Producer端
实质是在选择MessageQueue对象（内部包含了brokerName和queueId）,发送端指定message queue发送消息到相应的broker，来达到写入时的负载均衡

第一种是默认策略，从MessageQueue列表中随机选择一个，算法时通过自增随机数对列表打下取余得到位置信息，但获得的MessageQueue所在集群不能是上次失败集群。
>producer维护一个index、每次取节点会自增、index向所有broker个数取余

第二种是超时容忍策略，先随机选择一个MessageQueue，如果因为超时等异常发送失败，会优先选择该broker集群下其他MessageQueue发送，如果没找到就从之前发送失败的Broker集群中选一个进行发送，若还没有找到才使用默认策略。
> 自定义实现MessageQueueSelector接口中的select方法 ,MessageQueue select(final List<MessageQueue> mqs, final Message msg, final Object arg);

Producer的负载均衡是由MQFaultStratege.selectOneMessageQueue()来实现的。
这个方法就是随机选择一个要发送消息的broker来达到负载均衡的效果，
选择的标准：尽量不选刚刚选过的broker，尽量不选发送上条消息延迟过高或没有响应的broker，也就是找到一个可用的broker。（源码不贴了）

##### Consumer端
> 默认平均分配策略(默认)(AllocateMessageQueueAveragely) 

Consumer的负载均衡是指将MessageQueue中的消息队列分配到消费者组里的具体消费者。
Consumer在启动的时候会实例化rebalanceImpl，这个类负责消费端的负载均衡。通过rebalanceImpl调用allocateMesasgeQueueStratage.allocate()完成负载均衡。
每次有新的消费者加入到组中就会重新做一下分配。每10秒自动做一次负载均衡。

其他负载均衡算法
- 平均分配策略(默认)(AllocateMessageQueueAveragely) 
- 环形分配策略(AllocateMessageQueueAveragelyByCircle) 
- 手动配置分配策略(AllocateMessageQueueByConfig) 
- 机房分配策略(AllocateMessageQueueByMachineRoom) 
- 靠近机房策略(AllocateMachineRoomNearby)
- 一致性哈希分配策略(AllocateMessageQueueConsistentHash) 
    
    >使用一致性哈希算法进行负载，每次负载都会重建一致性hash路由表，获取本地客户端负责的所有队列信息。默认的hash算法为MD5，假设有4个消费者客户端和2个消息队列mq1和mq2，通过hash后分布在hash环的不同位置，按照一致性hash的顺时针查找原则，mq1被client2消费，mq2被client3消费。

##### 什么时候触发负载均衡:
- 消费者启动之后
- 消费者数量发生变更
- 每10秒会触发检查一次rebalance

追问：当消费负载均衡consumer和queue不对等的时候会发生什么？
Consumer和queue会优先平均分配，如果Consumer少于queue的个数，则会存在部分Consumer消费多个queue的情况，如果Consumer等于queue的个数，那就是一个Consumer消费一个queue，如果Consumer个数大于queue的个数，那么会有部分Consumer空余出来，白白的浪费了。

#### 11、消息重复消费
影响消息正常发送和消费的重要原因是网络的不确定性。

引起重复消费的原因

ACK
正常情况下在consumer真正消费完消息后应该发送ack，通知broker该消息已正常消费，从queue中剔除

当ack因为网络原因无法发送到broker，broker会认为词条消息没有被消费，此后会开启消息重投机制把消息再次投递到consumer

消费模式
在CLUSTERING模式下，消息在broker中会保证相同group的consumer消费一次，但是针对不同group的consumer会推送多次

解决方案

数据库表
处理消息前，使用消息主键在表中带有约束的字段中insert

Map
单机时可以使用map ConcurrentHashMap -> putIfAbsent   guava cache

Redis
分布式锁搞起来。

#### 12、如何让RocketMQ保证消息的顺序消费
你们线上业务用消息中间件的时候，是否需要保证消息的顺序性?

如果不需要保证消息顺序，为什么不需要?假如我有一个场景要保证消息的顺序，你们应该如何保证?

首先多个queue只能保证单个queue里的顺序，queue是典型的FIFO，天然顺序。多个queue同时消费是无法绝对保证消息的有序性的。所以总结如下：

同一topic，同一个QUEUE，发消息的时候一个线程去发送消息，消费的时候 一个线程去消费一个queue里的消息。

追问：怎么保证消息发到同一个queue？
Rocket MQ给我们提供了MessageQueueSelector接口，可以自己重写里面的接口，实现自己的算法，举个最简单的例子：判断i % 2 == 0，那就都放到queue1里，否则放到queue2里。


####  13、RocketMQ如何保证消息不丢失
Producer端、Broker端、Consumer端
##### Producer端如何保证消息不丢失
采取send()同步发消息，发送结果是同步感知的。
发送失败后可以重试，设置重试次数。默认3次。
producer.setRetryTimesWhenSendFailed(10);

集群部署，比如发送失败了的原因可能是当前Broker宕机了，重试的时候会发送到其他Broker上。
##### Broker端如何保证消息不丢失
修改刷盘策略为同步刷盘。默认情况下是异步刷盘的。
flushDiskType = SYNC_FLUSH

集群部署，主从模式，高可用。
#####  Consumer端如何保证消息不丢失
完全消费正常后在进行手动ack确认。
####  14、rocketMQ的消息堆积如何处理
下游消费系统如果宕机了，导致几百万条消息在消息中间件里积压，此时怎么处理?

你们线上是否遇到过消息积压的生产故障?如果没遇到过，你考虑一下如何应对?

首先要找到是什么原因导致的消息堆积，是Producer太多了，Consumer太少了导致的还是说其他情况，总之先定位问题。

然后看下消息消费速度是否正常，正常的话，可以通过上线更多consumer临时解决消息堆积问题

追问：如果Consumer和Queue不对等，上线了多台也在短时间内无法消费完堆积的消息怎么办？
准备一个临时的topic
queue的数量是堆积的几倍
queue分布到多Broker中
上线一台Consumer做消息的搬运工，把原来Topic中的消息挪到新的Topic里，不做业务逻辑处理，只是挪过去
上线N台Consumer同时消费临时Topic中的数据
改bug
恢复原来的Consumer，继续消费之前的Topic
追问：堆积时间过长消息超时了？
RocketMQ中的消息只会在commitLog被删除的时候才会消失，不会超时。也就是说未被消费的消息不会存在超时删除这情况。

追问：堆积的消息会不会进死信队列？
不会，消息在消费失败后会进入重试队列（%RETRY%+ConsumerGroup），18次（默认18次，网上所有文章都说是16次，无一例外。但是我没搞懂为啥是16次，这不是18个时间吗 ？）才会进入死信队列（%DLQ%+ConsumerGroup）。

源码如下：

public class MessageStoreConfig {
    // 每隔如下时间会进行重试，到最后一次时间重试失败的话就进入死信队列了。
 private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
}
####  15、RocketMQ在分布式事务支持这块机制的底层原理?
你们用的是RocketMQ?RocketMQ很大的一个特点是对分布式事务的支持，你说说他在分布式事务支持这块机制的底层原理?

分布式系统中的事务可以使用TCC（Try、Confirm、Cancel）、2pc来解决分布式系统中的消息原子性

RocketMQ 4.3+提供分布事务功能，通过 RocketMQ 事务消息能达到分布式事务的最终一致

RocketMQ实现方式：

**Half Message：**预处理消息，当broker收到此类消息后，会存储到RMQ_SYS_TRANS_HALF_TOPIC的消息消费队列中

**检查事务状态：**Broker会开启一个定时任务，消费RMQ_SYS_TRANS_HALF_TOPIC队列中的消息，每次执行任务会向消息发送者确认事务执行状态（提交、回滚、未知），如果是未知，Broker会定时去回调在重新检查。

**超时：**如果超过回查次数，默认回滚消息。

也就是他并未真正进入Topic的queue，而是用了临时queue来放所谓的half message，等提交事务后才会真正的将half message转移到topic下的queue。

#### 16、如果让你来动手实现一个分布式消息中间件，整体架构你会如何设计实现?
持久化的姿势、高可用性、数据0丢失的考虑、服务端部署简单、client端使用简单

#### 17、看过RocketMQ 的源码没有。如果看过，说说你对RocketMQ 源码的理解?
里面比较典型的设计模式有单例、工厂、策略、门面模式。单例工厂无处不在，
策略印象深刻比如发消息和消费消息的时候queue的负载均衡就是N个策略算法类，有随机、hash等，
这也是能够快速扩容天然支持集群的必要原因之一。持久化做的也比较完善，采取的CommitLog来落盘，同步异步两种方式。

#### 18、高吞吐量下如何优化生产者和消费者的性能?
开发
> - 同一group下，多机部署，并行消费
> - 单个Consumer提高消费线程个数
> - 批量消费
> - 消息批量拉取
> - 业务逻辑批量处理

运维
> - 网卡调优
> - jvm调优
> - 多线程与cpu调优
> - Page Cache
#### 19、再说说RocketMQ 是如何保证数据的高容错性的?
在不开启容错的情况下，轮询队列进行发送，如果失败了，重试的时候过滤失败的Broker
如果开启了容错策略，会通过RocketMQ的预测机制来预测一个Broker是否可用
如果上次失败的Broker可用那么还是会选择该Broker的队列
如果上述情况失败，则随机选择一个进行发送
在发送消息的时候会记录一下调用的时间与是否报错，根据该时间去预测broker的可用时间
其实就是send消息的时候queue的选择。源码在如下：

org.apache.rocketmq.client.latency.MQFaultStrategy#selectOneMessageQueue()

#### 20、任何一台Broker突然宕机了怎么办？
Broker主从架构以及多副本策略。Master收到消息后会同步给Slave，这样一条消息就不止一份了，Master宕机了还有slave中的消息可用，保证了MQ的可靠性和高可用性。而且Rocket MQ4.5.0开始就支持了Dlegder模式，基于raft的，做到了真正意义的HA。

#### 21、Broker把自己的信息注册到哪个NameServer上？
这么问明显在坑你，因为Broker会向所有的NameServer上注册自己的信息，而不是某一个，是每一个，全部！




#### 24、如何保证顺序消息？
顺序由producer发送到broker的消息队列是满足FIFO的，所以发送是顺序的，单个queue里的消息是顺序的。多个Queue同时消费是无法绝对保证消息的有序性的。所以，同一个topic，同一个queue，发消息的时候一个线程发送消息，消费的时候一个线程去消费一个queue里的消息。
追问：怎么保证消息发到同一个queue里？
RocketMQ给我们提供了MessageQueueSelector接口，可以重写里面的接口，实现自己的算法，比如判断i%2==0，那就发送消息到queue1否则发送到queue2。

#### 25、如何实现消息过滤？
有两种方案，一种是在broker端按照Consumer的去重逻辑进行过滤，这样做的好处是避免了无用的消息传输到Consumer端，缺点是加重了Broker的负担，实现起来相对复杂。另一种是在Consumer端过滤，比如按照消息设置的tag去重，这样的好处是实现起来简单，缺点是有大量无用的消息到达了Consumer端只能丢弃不处理。

#### 26、消息去重

如果由于网络等原因，多条重复消息投递到了Consumer端，你怎么进行消息去重？
这个得先说下消息的幂等性原则：就是用户对于同一种操作发起的多次请求的结果是一样的，不会因为操作了多次就产生不一样的结果。只要保持幂等性，不管来多少条消息，最后处理结果都一样，需要Consumer端自行实现。
去重的方案：因为每个消息都有一个MessageId, 保证每个消息都有一个唯一键，可以是数据库的主键或者唯一约束，也可以是Redis缓存中的键，当消费一条消息前，先检查数据库或缓存中是否存在这个唯一键，如果存在就不再处理这条消息，如果消费成功，要保证这个唯一键插入到去重表中。


#### 27、分布式事务消息

半消息：是指暂时还不能被Consumer消费的消息，Producer成功发送到broker端的消息，但是此消息被标记为“暂不可投递”状态，只有等Producer端执行完本地事务后经过二次确认了之后，Consumer才能消费此条消息。

![RockerMQ 2pc事务](./../resouce/RockerMQ 2pc事务.png)

上图就是分布式事务消息的实现过程，依赖半消息，二次确认以及消息回查机制。
- 1、Producer向broker发送半消息
- 2、Producer端收到响应，消息发送成功，此时消息是半消息，标记为“不可投递”状态，Consumer消费不了。
- 3、Producer端执行本地事务。
- 4、正常情况本地事务执行完成，Producer向Broker发送Commit/Rollback，如果是Commit，Broker端将半消息标记为正常消息，Consumer可以消费，如果是Rollback，Broker丢弃此消息。
- 5、异常情况，Broker端迟迟等不到二次确认。在一定时间后，会查询所有的半消息，然后到Producer端查询半消息的执行情况。
- 6、Producer端查询本地事务的状态
- 7、根据事务的状态提交commit/rollback到broker端。（5，6，7是消息回查）

#### 28、消息的可用性

RocketMQ如何能保证消息的可用性/可靠性？（这个问题的另一种问法：如何保证消息不丢失）
答案如下，要从Producer，Consumer和Broker三个方面来回答。
从Producer角度分析，如何确保消息成功发送到了Broker？
1、可以采用同步发送，即发送一条数据等到接受者返回响应之后再发送下一个数据包。如果返回响应OK，表示消息成功发送到了broker，状态超时或者失败都会触发二次重试。
2、可以采用分布式事务消息的投递方式。
3、如果一条消息发送之后超时，也可以通过查询日志的API，来检查是否在Broker存储成功。
总的来说，Producer还是采用同步发送来保证的。
从Broker角度分析，如何确保消息持久化?
1、消息只要持久化到CommitLog（日志文件）中，即使Broker宕机，未消费的消息也能重新恢复再消费。
2、Broker的刷盘机制：同步刷盘和异步刷盘，不管哪种刷盘都可以保证消息一定存储在pagecache中（内存中），但是同步刷盘更可靠，它是Producer发送消息后等数据持久化到磁盘之后再返回响应给Producer。
3、Broker支持多Master多Slave同步双写和多Master多Slave异步复制模式，消息都是发送给Master主机，但是消费既可以从Master消费，也可以从Slave消费。同步双写模式可以保证即使Master宕机，消息肯定在Slave中有备份，保证了消息不会丢失。
从Consumer角度分析，如何保证消息被成功消费？
Consumer自身维护了个持久化的offset（对应Message Queue里的min offset），用来标记已经成功消费且已经成功发回Broker的消息下标。如果Consumer消费失败，它会向Broker发回消费失败的状态，发回成功才会更新自己的offset。如果发回给broker时broker挂掉了，Consumer会定时重试，如果Consumer和Broker一起挂掉了，消息还在Broker端存储着，Consumer端的offset也是持久化的，重启之后继续拉取offset之前的消息进行消费。

#### 29、刷盘实现

RocketMQ提供了两种刷盘策略：同步刷盘和异步刷盘
同步刷盘：在消息达到Broker的内存之后，必须刷到commitLog日志文件中才算成功，然后返回Producer数据已经发送成功。
异步刷盘：异步刷盘是指消息达到Broker内存后就返回Producer数据已经发送成功，会唤醒一个线程去将数据持久化到CommitLog日志文件中。
优缺点分析：同步刷盘保证了消息不丢失，但是响应时间相对异步刷盘要多出10%左右，适用于对消息可靠性要求比较高的场景。异步刷盘的吞吐量比较高，RT小，但是如果broker断电了内存中的部分数据会丢失，适用于对吞吐量要求比较高的场景。


#### 31、PushConsumer,PullConsumer消费模式分析

Push:实时性高，但是增加服务器负载，消费能力不同，如果push过快，消费端会出现很多问题；

Pull:消费者从Server端拉取资源，主动权在消费端，可控性好，但是间隔时间不好控制，间隔时间太短，则空请求，资源浪费，间隔时间太长，则消息不能及时处理；

长轮询：Client请求Server服务端（Broker）,Broker会保持一段时间的连接，默认是15s,如果这段时间没有消息达到，则离开返回给Customer,没有消息的话，超过15s,则返回空，再进行重新请求，缺点：服务端需要保持Customer的请求，会占用资源，需要客户端连接数可控，否则会一段连接。

##### PushConsumer本质是长轮询；
- 1.系统收到消息后自动处理消息和offset,如果有新的Consumer加入会自动做负载均衡，
- 2.在broker端可以通过longPolling=true来开启长轮询，
- 3.消费端代码：DefaultMQPushConsumerImpl->pullMessage->PullCallback
- 4.服务端代码：broker.longpolling
- 5.虽然是push，但代码里面大量使用了pull,是因为使用了长轮询方式达到push效果，既有pull有的，又有push的实现性。
- 6.关闭优雅：只要是释放资源和保存offset,调用shutdown()即可，参考@PostConstruct,@PreDestory。

##### PullConsumer:需要自己维护offset;
- 1.获取MessageQueue遍历；
- 2.客户维护offset，需要本地用户存储offset,存储内存，磁盘数据库等；
- 3.处理不同状态的消息FOUND,NO_NEW_MSG,OFFSET_ILLRGL,NO_MATCHED_MSG,4种状态；
- 4.灵活性强，但编码复杂度高；
- 5.关闭优雅，注意是释放资源和保存offset,需要程序自己保存offset,特别是异常处理的时候；
