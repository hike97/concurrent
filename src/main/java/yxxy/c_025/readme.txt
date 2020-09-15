总结：
1：对于map/set的选择使用
HashMap
TreeMap
LinkedHashMap
//并发量小的时候可用
Hashtable
Collections.sychronizedXXX
//并发量大
ConcurrentHashMap
//并发量大需要排序
ConcurrentSkipListMap 

2：队列
ArrayList
LinkedList
Collections.synchronizedXXX
CopyOnWriteList //写少读多时使用
Queue
	CocurrentLinkedQueue //concurrentArrayQueue 内部加锁队列
	BlockingQueue                               阻塞队列
		LinkedBQ //无界队列
		ArrayBQ //有界队列
		TransferQueue
		SynchronusQueue
	DelayQueue 执行定时任务
		
	