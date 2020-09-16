package interview.proandcon;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Author hike97
 * @Description 生产者和消费者模式 版本1 ：wait 和 notify
 * @create 2020-09-15 10:03
 * @Modified By:
 **/
public class ProducterAndConsumerV1 {
	public static void main (String[] args) {
//		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder ().setNameFormat ("demo-pool-%d").build ();
//		ExecutorService singleThreadPool = new ThreadPoolExecutor (5, 10,
//				0L, TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable> (1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy ());

//		while (true){
//			singleThreadPool.execute (new ProductThread (man));
//			singleThreadPool.execute (new ConsumerThread (man));
//			singleThreadPool.execute (new ProductThread (man));
//			singleThreadPool.execute (new ProductThread (man));
//		}


		//		proExecutor.shutdown ();
		//		conExecutor.shutdown ();
		SaleMan man = new SaleMan ();
		ProductThread productThread = new ProductThread (man);
		ConsumerThread consumerThread = new ConsumerThread (man);
		for (int i = 0; i <10 ; i++) {
			new Thread (productThread,"生产者").start ();
			new Thread (consumerThread,"消费者").start ();
		}




	}
}

/**
 * 店员 共同资源
 * 消费者线程 和 生产者线程共同持有
 */
class SaleMan {
	private int product;

	public synchronized void getProduct () throws Exception {
		while (product > 0) {
			this.wait ();
		}
		product++;
		System.out.println (Thread.currentThread ().getName () + " 生产：remain:" + product + "个产品~");
		Thread.sleep (500);
		this.notifyAll ();
	}

	public synchronized void saleProduct () throws Exception {
		while (product <= 0) {
			this.wait ();
		}
		Thread.sleep (200);
		product--;
		System.out.println (Thread.currentThread ().getName () + " 卖出：remain:" + product + "个产品~");
		this.notifyAll ();
	}
}

/**
 * 生产者线程
 */
class ProductThread implements Runnable {

	private SaleMan saleMan;

	public ProductThread (SaleMan saleMan) {
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
class ConsumerThread implements Runnable {

	private SaleMan saleMan;

	public ConsumerThread (SaleMan saleMan) {
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
