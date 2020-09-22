package interview.threadpool;

import java.util.concurrent.*;

/**
 * @Author hike97
 * @Description
 * @create 2020-09-18 14:43
 * @Modified By:
 **/
public class CustomThreadPoool {
	public static void main (String[] args) {
		/**
		 * cpu 密集型 maximumPoolSize 核心数+1
		 */
		/**
		 * io 密集型
		 * 方案一:cpu/1-阻塞系数（0.9~0.8）
		 * 4核 40~20
		 * 方案二：
		 *  核数*2
		 */
		System.out.println (Runtime.getRuntime ().availableProcessors ());
		ExecutorService threadPool = new ThreadPoolExecutor (
				2,
				5,
				1L,
				TimeUnit.SECONDS,
				new LinkedBlockingDeque<> (3),
				Executors.defaultThreadFactory (),
//				new ThreadPoolExecutor.AbortPolicy ()
				//谁调用 就退给谁 main 线程调用 最后一个任务由main调用
//				new ThreadPoolExecutor.CallerRunsPolicy ()
				/*
				 *抛弃阻塞时间最长的，并重新提交回队列
				 */
//				new ThreadPoolExecutor.DiscardOldestPolicy ()
				/*
				 * 直接抛弃
				 */
				new ThreadPoolExecutor.DiscardPolicy ()
		);
		//abortPolicy 直接拒绝
		//
		try {
			for (int i = 1; i <= 10; i++) {
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
	}
}
