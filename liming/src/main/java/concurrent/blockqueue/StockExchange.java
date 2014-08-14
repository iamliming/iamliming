package concurrent.blockqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-12 17:06
 * @id $Id$
 */
public class StockExchange {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> stockQueue = new LinkedBlockingQueue<Integer>();
		Seller seller = new Seller(stockQueue);
		Buyer buyer = new Buyer(stockQueue);
		Thread[] sellers = new Thread[10];
		for(int i = 0; i < 10; i++){
			sellers[i] =new Thread(seller,""+i);
			sellers[i].start();
		}
		Thread[] buyers = new Thread[10];
		for(int i = 0; i < 10; i++){
			buyers[i] =new Thread(buyer,""+i);
			buyers[i].start();
		}

		Thread.sleep(5000l);

		for(Thread t : sellers){
			t.interrupt();
		}
		for(Thread t :buyers){
			t.interrupt();
		}
	}

}


class Seller implements Runnable {
	private BlockingQueue orderQueue;
	Seller(BlockingQueue orderQueue) {
		this.orderQueue = orderQueue;
	}

	@Override public void run() {
		while(!Thread.interrupted()){
			try {
				Integer num = (int)(Math.random()*100);

				orderQueue.put(num);
				System.out.println("seller:"+Thread.currentThread().getName()+"-->"+num);
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				System.out.println("seller:out"+Thread.currentThread().getName());
				return;
			}
		}
		System.out.println("seller:interrupted:"+Thread.currentThread().getName());
	}

}


class Buyer implements Runnable {
	private BlockingQueue<Integer> orderQueue;

	Buyer(BlockingQueue<Integer> orderQueue) {
		this.orderQueue = orderQueue;
	}

	@Override public void run() {
		while(!Thread.interrupted()){
			try {
				Integer num = orderQueue.take();
				System.out.println("buy:"+Thread.currentThread().getName()+"-->"+num);
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				System.out.println("buy:out"+Thread.currentThread().getName());
				return;
			}
		}
		System.out.println("buy:interrupted:"+Thread.currentThread().getName());
	}
}
