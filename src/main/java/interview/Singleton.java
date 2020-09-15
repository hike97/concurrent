package interview;

/**
 * @Author hike97
 * @Description 双重校验锁实现对象单例 DLC 双重锁校验
 * @create 2020-08-31 18:46
 * @Modified By:
 **/
public class Singleton {
	//volatile 作用 程序可见性 防止指令重排
	private volatile static Singleton uniqueInstance;

	private Singleton () {
	}

	public static Singleton getUniqueInstance(){
		if (uniqueInstance == null){
			synchronized (Singleton.class){
				if (uniqueInstance == null) uniqueInstance = new Singleton ();
			}
		}
		return uniqueInstance;
	}
}

