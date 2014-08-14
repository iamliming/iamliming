package concurrent.concurrence;

/**
 * mutable 对象测试
 * Rbg的方法都是synchronized的
 * 线程1没在synchronized块中调用rgb对象的两个方法
 * 两次的结果是不同的.已经不是同一个对象,
 * 这里刻意让线程1 sleep了3秒,已使在这中间模拟这种情况
 *
 * 在存在锁争用的情况下,对可变对象的连续调用需要增加synchronized块
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-06 15:32
 * @id $Id$
 */
public class MutableTest {

	public static void main(String[] args) {

		SynchronizedRGB rgb = new SynchronizedRGB(11, 12, 13, "abc");
		Runnable runnable1 = () -> {
//			synchronized (rgb) {
				System.out.println(Thread.currentThread().getName() + "--getting-->" + rgb.getRGB());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "--getting-->" + rgb.getRGB());
//			}
		};

		Runnable runnable2 = () -> {
			try {
				Thread.sleep(1000);
				rgb.set(11, 11, 11, "bbc");
				System.out.println(Thread.currentThread().getName() + "--setting-->" + rgb.getRGB());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		new Thread(runnable1).start();
		new Thread(runnable2).start();
	}
}

class SynchronizedRGB {

	// Values must be between 0 and 255.
	private int red;
	private int green;
	private int blue;
	private String name;

	private void check(int red,
			int green,
			int blue) {
		if (red < 0 || red > 255
				|| green < 0 || green > 255
				|| blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	public SynchronizedRGB(int red,
			int green,
			int blue,
			String name) {
		check(red, green, blue);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.name = name;
	}

	public void set(int red,
			int green,
			int blue,
			String name) {
		check(red, green, blue);
		synchronized (this) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.name = name;
		}
	}

	public synchronized int getRGB() {
		return ((red << 16) | (green << 8) | blue);
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void invert() {
		red = 255 - red;
		green = 255 - green;
		blue = 255 - blue;
		name = "Inverse of " + name;
	}
}
