package concurrent.blockqueue;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-13 13:53
 * @id $Id$
 */
public class LuckNumberGenerator {

	public static void main(String[] args) throws InterruptedException {

		TransferQueue<String> queue = new LinkedTransferQueue<>();
		Thread product = new Thread(new Producer(queue));
		product.start();
		for(int i = 0; i < 10; i++){
			Thread comsumer = new Thread(new Consumer(queue));
			comsumer.start();
			Thread.sleep(1000l);
		}
	}
}

class Producer implements Runnable {

	private final TransferQueue<String> queue;

	Producer(TransferQueue<String> queue) {

		this.queue = queue;
	}

	private String produce() {

		return "your luck number :" + new Random().nextInt(100);
	}

	@Override public void run() {

		try {
			while (true) {
				if (queue.hasWaitingConsumer()) {
					queue.transfer(produce());
				}
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Consumer implements Runnable {

	private final TransferQueue<String> queue;

	Consumer(TransferQueue<String> queue) {

		this.queue = queue;
	}

	@Override
	public void run() {

		try {
//			String rst = queue.take();
			System.out.println(Thread.currentThread().getName() + "--->" + queue.take());
			System.out.println(Thread.currentThread().getName() + "--->" + queue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
