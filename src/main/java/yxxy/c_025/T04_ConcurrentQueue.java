package yxxy.c_025;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T04_ConcurrentQueue {
	public static void main(String[] args) {
		Queue<String> strs = new ConcurrentLinkedQueue<>(); //无界队列
		
		for(int i=0; i<10; i++) {
			strs.offer("a" + i);  //add
		}
		
		System.out.println(strs);
		
		System.out.println(strs.size());
		
		System.out.println(strs.poll());//拿出来 删掉
		System.out.println(strs.size());
		
		System.out.println(strs.peek());//拿出来 不删
		System.out.println(strs.size());
		
		//双端队列Deque
	}
}
