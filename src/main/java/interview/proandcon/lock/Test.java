package interview.proandcon.lock;

/**
 * @Author hike97
 * @Description
 * @create 2020-09-15 19:26
 * @Modified By:
 **/
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class MyService {

	private ReentrantLock lock = new ReentrantLock();

	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();

	public void method() {
		lock.lock();
		try {
			for (int i = 1; i <= 3; i++) {
				Thread.sleep(1000);
				System.out.println("ThreadName=" + Thread.currentThread().getName() + "  " + i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void methodA() {
		lock.lock();
		try {
			System.out.println("MethodA begin ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
			conditionA.await();
			System.out.println("MethodA end ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void methodB() {
		lock.lock();
		try {
			System.out.println("MethodB begin ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
			conditionB.await();
			System.out.println("MethodB end ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void signalA() {
		lock.lock();
		conditionA.signal();
		lock.unlock();
	}
	public void signalA_All() {
		lock.lock();
		conditionA.signalAll();
		lock.unlock();
	}


	public void signalB() {
		lock.lock();
		conditionB.signal();
		lock.unlock();
	}
	public void signalB_All() {
		lock.lock();
		conditionB.signalAll();
		lock.unlock();
	}
}

class MyThread extends Thread {

	private MyService service;

	MyThread(MyService service) {
		this.service = service;
	}

	@Override
	public void run() {
		service.method();
	}
}

class ThreadA extends Thread {

	private MyService service;

	ThreadA(MyService service) {
		this.service = service;
	}

	@Override
	public void run() {
		service.methodA();
	}
}

class ThreadB extends Thread {

	private MyService service;

	ThreadB(MyService service) {
		this.service = service;
	}

	@Override
	public void run() {
		service.methodB();
	}
}

public class Test {

	public static void main(String[] args) throws InterruptedException {
		MyService service = new MyService();
		// 多线程 同一方法互斥

//		MyThread myThread1 = new MyThread(service);
//		MyThread myThread2 = new MyThread(service);
//
//		myThread1.start();
//		myThread2.start();

		//多线程执行不同代码块互斥

//		ThreadA threadA = new ThreadA(service);
//		threadA.setName("A");
//
//		ThreadB threadB = new ThreadB(service);
//		threadB.setName("B");
//		threadA.start();
//		threadB.start();
//
//		Thread.sleep(1000);
//		System.out.println ("执行唤醒A线程方法");
//		service.signalA();
//		Thread.sleep(1000);
//		service.signalB();

		ThreadA[] threadAs = new ThreadA[10];
		for (int i=0;i<5;i++){
			threadAs[i] = new ThreadA(service);
		}
		ThreadB[] threadBs = new ThreadB[10];
		for (int i=0;i<5;i++){
			threadBs[i] = new ThreadB(service);
		}

		for (int i=0;i<5;i++){
			threadAs[i].start();
			threadBs[i].start();
		}

		Thread.sleep(1000);
		System.out.println ("唤醒全部A线程");
		service.signalA_All();
		Thread.sleep(1000);
		System.out.println ("唤醒全部B线程");
		service.signalB_All();
	}
}
