### mysql事务
DEFAULT

使用底层数据存储的默认隔离级别。MySQL的默认隔离级别是REPEATABLE-READ。

**READ_UNCOMMITTED**

读未提交。脏读、不可重复读、幻读都会发生。

**READ_COMMITTED**

读已提交。脏读不会发生，不可重复读、幻读都会发生。

**REPEATABLE_READ**

可重复读。脏读、不可重复读都不会发生，幻读会发生。

**SERIALIZABLE**

可串行化。脏读、不可重复读、幻读都不会发生