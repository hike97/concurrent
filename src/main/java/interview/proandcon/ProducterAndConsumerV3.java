package interview.proandcon;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author hike97
 * @Description 生产者和消费者模式 版本1 ：wait 和 notify
 * @create 2020-09-15 10:03
 * @Modified By:
 **/
public class ProducterAndConsumerV3 {
	public static void main (String[] args) throws InterruptedException {
		SaleMan3 man = new SaleMan3 (new ArrayBlockingQueue<> (10));
		ProductThread3 productThread = new ProductThread3 (man);
		ConsumerThread3 consumerThread = new ConsumerThread3 (man);
		ExecutorService service = Executors.newFixedThreadPool (100);
		service.execute (productThread);
		service.execute (consumerThread);
//		System.out.println ("生产者线程启动");
//		new Thread (productThread, "生产者V3").start ();
//		System.out.println ("消费者线程启动");
//		new Thread (consumerThread, "消费者V3").start ();
		TimeUnit.SECONDS.sleep (5);
		man.stop ();
		System.out.println ("5s passed , program end");
	}
}

/**
 * 店员 共同资源
 * 消费者线程 和 生产者线程共同持有
 */
class SaleMan3 {
	//
	private volatile boolean flag = true;
	private volatile AtomicInteger product = new AtomicInteger ();
	BlockingQueue<String> blockingQueue = null;

	public SaleMan3 (BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
		System.out.println (blockingQueue.getClass ().getName ());
	}
    //生产
	public void getProduct () throws Exception {
		String data = null;
		boolean result;
		while (flag) {
			//如果是生产状态 +1
			data = product.incrementAndGet () + "";
			result = blockingQueue.offer (data, 2, TimeUnit.SECONDS);
			if (result) {
				System.out.println (Thread.currentThread ().getName () + "\t插入队列" + data + "成功");
			} else {
				System.out.println (Thread.currentThread ().getName () + "\t插入队列" + data + "失败");
			}
			TimeUnit.SECONDS.sleep (1);
		}
		System.out.println (Thread.currentThread ().getName () + "\t开关关闭，FLAG =false，生产结束");
	}
    //消费
	public void saleProduct () throws Exception {
		String result = null;
		while (flag){
			result = blockingQueue.poll (2L, TimeUnit.SECONDS);
			if (null == result||result.equalsIgnoreCase ("")){
				flag=false;
				System.out.println (Thread.currentThread ().getName () + "\t获取产品超时2S，消费退出");
				System.out.println ();
				System.out.println ();
				return;
			}
			System.out.println (Thread.currentThread ().getName () + "\t销售产品"+result+"成功");

		}

	}

	public void stop(){
		this.flag = false;
	}
}

/**
 * 生产者线程
 */
class ProductThread3 implements Runnable {

	private SaleMan3 saleMan;

	public ProductThread3 (SaleMan3 saleMan) {
		this.saleMan = saleMan;
	}

	@Override
	public void run () {
		try {
			saleMan.getProduct ();
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
}

/**
 * 消费者线程
 */
class ConsumerThread3 implements Runnable {

	private SaleMan3 saleMan;

	public ConsumerThread3 (SaleMan3 saleMan) {
		this.saleMan = saleMan;
	}

	@Override
	public void run () {
		try {
			saleMan.saleProduct ();
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
}
