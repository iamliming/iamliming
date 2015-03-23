package practice.concurrency.five;

import java.util.concurrent.CountDownLatch;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-3-20 下午1:55
 */
public class TestHarness {
	public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch end = new CountDownLatch(nThreads);
		for(int i = 0; i < nThreads; i ++){
			new Thread(){
				@Override
				public void run () {
					try {
						start.await();
						task.run();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finally {
						end.countDown();
					}

				}
			}.start();
		}
		long startTime = System.nanoTime();
		start.countDown();
		end.await();
//		end.await(1l, TimeUnit.NANOSECONDS);
		long endTime = System.nanoTime();
		return endTime - startTime;
	}

	public static void main (String[] args) throws InterruptedException {
		long time = timeTasks(10, new Runnable() {
			@Override
			public void run () {
				System.out.println(Thread.currentThread().getName());
			}
		});
		System.out.println("time escape:" + time);
	}
}
