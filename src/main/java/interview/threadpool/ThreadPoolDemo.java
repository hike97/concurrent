package interview.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Author hike97
 * @Description 线程池实现
 * @create 2020-09-17 17:39
 * @Modified By:
 **/
public class ThreadPoolDemo {
	public static void main (String[] args) {
		//尽量不要用线程工具类创建线程 容易导致oom
			/*
			    newFixedThreadPool|newSingeledThreadPool 队列最大数为Integer.MAX 导致OOM可能
			    newCachedThreadPool maximumPoolSize 会导致OOM
			 */
		//		ExecutorService service = Executors.newFixedThreadPool (5);
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder ().setNameFormat ("demo-pool-%d").build ();
		ExecutorService threadPool = new ThreadPoolExecutor (2, 5,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable> (3), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy ());
		try {
			for (int i = 1; i <= 9; i++) {
				final int tempI = i;
				threadPool.execute (() -> {
					System.out.println (Thread.currentThread ().getName () + "号窗口，" + "服务客户号" + tempI);
					try {
						TimeUnit.SECONDS.sleep (4);
					} catch (InterruptedException e) {
						e.printStackTrace ();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace ();
		} finally {
			threadPool.shutdown ();
		}
		/*
		   out put:
		   demo-pool-1号窗口，服务客户号2
		   demo-pool-0号窗口，服务客户号1

		   //线程池常驻线程 满了后 后续任务会加入队列等待
		   //队列满了后 会开启额外线程 如果此时再次有任务加入
		   //直接获取新开线程执行任务 之后才会执行队列中等待任务
		   //超过线程等待时间后 当前线程大于corePoolSize 则该线程关闭
		   demo-pool-2号窗口，服务客户号6
		   /---------------------------------------
		   demo-pool-2号窗口，服务客户号3
		   demo-pool-0号窗口，服务客户号4   new LinkedBlockingQueue<Runnable> (3) 队列中等待执行的三个任务 3 4 5
		   demo-pool-1号窗口，服务客户号5
		   /---------------------------------------
		 */
	}
}
