package concurrent.samphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-13 14:35
 * @id $Id$
 */
public class Bank {

	public volatile static int num = 0;
	public volatile static int leave = 0;

	public static void main(String[] args) throws InterruptedException {

		Semaphore semaphore = new Semaphore(2, true);
		Runnable casher = () -> {
			try {
				if (semaphore.tryAcquire(20, TimeUnit.MILLISECONDS)) {
					long time = new Random().nextInt(5);
					System.out.println(Thread.currentThread().getName() + "--->" + time);
					num++;
					Thread.sleep(time);
				} else {
					leave++;
					System.out.println(Thread.currentThread().getName() + "--->leave");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
			}
		};

		ExecutorService service = Executors.newCachedThreadPool();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			service.submit(casher);
		}
		service.shutdown();
		while (!service.isTerminated()) {
			TimeUnit.MILLISECONDS.sleep(1);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(num);
		System.out.println(leave);
	}
}
