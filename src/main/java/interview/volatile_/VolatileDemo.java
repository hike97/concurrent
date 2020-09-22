package interview.volatile_;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author hike97
 * @Description volatile 关键字使用
 * @create 2020-09-18 15:49
 * @Modified By:
 **/
public class VolatileDemo {
	/**
	 * Volatile 关键字使用
	 * @param args
	 */
	public static void main (String[] args) {
		DecimalFormat format = new DecimalFormat ("0.0");
		String s = format.format (new BigDecimal ("0.05"));
		System.out.println (s);
	}
}
class MyData {
	volatile int number;

	public void add(){

	}
}