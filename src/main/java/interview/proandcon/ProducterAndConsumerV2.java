package interview.proandcon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author hike97
 * @Description 生产者和消费者模式 版本1 ：wait 和 notify
 * @create 2020-09-15 10:03
 * @Modified By:
 **/
public class ProducterAndConsumerV2 {
	public static void main (String[] args) {
		SaleMan2 man = new SaleMan2();
		ProductThread2 productThread = new ProductThread2 (man);
		ConsumerThread2 consumerThread = new ConsumerThread2 (man);
		for (int i = 0; i <10 ; i++) {
			new Thread (productThread,"生产者V2").start ();
			new Thread (consumerThread,"消费者V2").start ();
		}
	}
}

/**
 * 店员 共同资源
 * 消费者线程 和 生产者线程共同持有
 */
class SaleMan2 {
	private int product;
	private Lock lock = new ReentrantLock ();
	private Condition productCondition = lock.newCondition ();
	private Condition consumerCondition = lock.newCondition ();

	public void getProduct () throws Exception {
		lock.lock ();
		try {
			while (product > 0) {
				productCondition.await ();
			}
			product++;
			System.out.println (Thread.currentThread ().getName () + " 生产：remain:" + product + "个产品~");
			Thread.sleep (500);
			consumerCondition.signal ();
		} catch (Exception e) {
			e.printStackTrace ();
		} finally {
			lock.unlock ();
		}
	}

	public void saleProduct () throws Exception {
		lock.lock ();
		try {
			while (product <= 0) {
				consumerCondition.await ();
			}
			Thread.sleep (200);
			product--;
			System.out.println (Thread.currentThread ().getName () + " 卖出：remain:" + product + "个产品~");
			productCondition.signal ();
		} catch (Exception e) {
			e.printStackTrace ();
		} finally {
			lock.unlock ();
		}


	}
}

/**
 * 生产者线程
 */
class ProductThread2 implements Runnable {

	private SaleMan2 saleMan;

	public ProductThread2 (SaleMan2 saleMan) {
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
class ConsumerThread2 implements Runnable {

	private SaleMan2 saleMan;

	public ConsumerThread2 (SaleMan2 saleMan) {
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
