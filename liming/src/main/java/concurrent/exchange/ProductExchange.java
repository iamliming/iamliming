package concurrent.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-13 16:29
 * @id $Id$
 */
public class ProductExchange {

	public static Exchanger<List<String>> exchanger = new Exchanger<>();

	public static void main(String[] args) {
		Thread pro = new Thread(new Product());
		pro.start();
		Thread com = new Thread(new Comsumer());
		com.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pro.interrupt();
		com.interrupt();
	}

}

class Product implements Runnable{

	List<String> cakes = new ArrayList<>();
	public volatile static int num = 0;
	private boolean end = false;

	@Override public void run() {
		while(!end && num != 100) {
			if (cakes.isEmpty()) {
				for (int i = 0; i < 10; i++) {
					cakes.add("cake" + ++num);
				}
				System.out.println("product-->" + cakes);
				try {
					cakes = ProductExchange.exchanger.exchange(cakes);
				} catch (InterruptedException e) {
					end = true;
				}
			}
		}
	}
}

class Comsumer implements Runnable{

	List<String> cakes = new ArrayList<>();
	private boolean end = false;

	@Override public void run() {
		while(!end) {
			if (cakes.isEmpty()) {
				try {
					long start = System.nanoTime();
					cakes = ProductExchange.exchanger.exchange(cakes);
					long end = System.nanoTime();
					System.out.println("comsumer-->" + cakes);
					cakes.clear();
				} catch (InterruptedException e) {
					end = true;
				}
			}
		}
	}
}

