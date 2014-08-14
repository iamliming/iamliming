package concurrent.collection;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-05 20:31
 * @id $Id$
 */
public class ArrayBlockQueueTest {

	public static void main(String[] args) {

		BlockingQueue<Cake> arrayQueue = new ArrayBlockingQueue<Cake>(10);
		Product pro = new Product(arrayQueue);
		Comsumer comsumer1 = new Comsumer(arrayQueue);
		Comsumer comsumer2 = new Comsumer(arrayQueue);
		new Thread(pro, "product").start();
		new Thread(comsumer1, "1comsumer").start();
		new Thread(comsumer2, "2comsumer").start();
	}
}

class Comsumer implements Runnable {

	private BlockingQueue<Cake> blockingQueue;

	Comsumer(BlockingQueue<Cake> blockingQueue) {

		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		try {
			Cake cake = null;
			while (!(cake = blockingQueue.take()).getName().equals("exit")) {
				System.out.println(blockingQueue.size() + "-->" + Thread.currentThread().getName() + "----->" + cake);
				Thread.sleep(15);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Product implements Runnable {

	private BlockingQueue<Cake> blockingQueue;

	Product(BlockingQueue<Cake> blockingQueue) {

		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		try {
			for (int i = 0; i < 100; i++) {
				Cake cake = new Cake(i, "cake" + i);
				blockingQueue.put(cake);
				System.out.println(blockingQueue.size() + "-->" + "product--->" + i + "-->" + cake);
				Thread.sleep(10);
			}
			Cake cake = new Cake(100, "exit");
			blockingQueue.put(cake);
			System.out.println(blockingQueue.size() + "-->" + "product-->" + cake);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Cake implements Serializable {

	private String name;
	private Integer num;

	Cake(Integer num, String name) {

		this.num = num;
		this.name = name;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Integer getNum() {

		return num;
	}

	public void setNum(Integer num) {

		this.num = num;
	}

	@Override public String toString() {

		return "Cake{" +
				"name='" + name + '\'' +
				", num=" + num +
				'}';
	}
}
