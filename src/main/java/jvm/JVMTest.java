package jvm;

/**
 * @Author hike97
 * @Description
 * @create 2020-09-01 12:15
 * @Modified By:
 **/
public class JVMTest {
	public static final int value = 3;

	public static void main (String[] args) throws ClassNotFoundException {
		/**
		 * 类加载器 启动类加载器 bootstrap
		 * 		   拓展类加载器 extension classloader
		 * 		   application
		 */
		ClassLoader classLoader = Thread.currentThread ().getContextClassLoader ();
		System.out.println (classLoader);
		System.out.println (classLoader.getParent ());
		System.out.println (classLoader.getParent ().getParent ());
		//使用ClassLoader.loadClass()来加载类，不会执行初始化块
//		classLoader.loadClass ("jvm.Test2");
		//使用Class.forName()来加载类，默认会执行初始化块
//		Class.forName("jvm.Test2");
		//使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
//		双亲委派模型

	}
}
