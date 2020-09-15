package yxxy.c_025;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class T06_ArrayBlockingQueue {
	/**
	 * 有界队列
	 */
	static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

	static Random r = new Random();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			strs.put("a" + i);
		}
		
//		strs.put("aaa"); //满了就会等待，程序阻塞
		//strs.add("aaa");
		boolean aaa = strs.offer ("aaa");
		System.out.println (aaa);
		//strs.offer("aaa", 1, TimeUnit.SECONDS); //按时间段添加
		
		System.out.println(strs);
	}
}
