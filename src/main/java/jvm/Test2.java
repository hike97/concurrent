package jvm;

/**
 * @Author hike97
 * @Description
 * @create 2020-09-01 12:29
 * @Modified By:
 **/
public class Test2 {
	static {
		System.out.println ("静态初始化块执行!");
	}

	public int calc (int n) {
		try {
			n += 1;
			if (n / 0 > 0) {
				n += 1;
			} else {
				n -= 10;
			}
			return n;
		} catch (Exception e) {
			n++;
		}
		n++;
		return n++;
	}
}
