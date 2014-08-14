package concurrent.futuretask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-13 17:38
 * @id $Id$
 */
public class StocksOrderProcessor {

	static final int MAX_NUMBER = 1000;
	static private ExecutorService executorService = Executors.newFixedThreadPool(100);
	static private List<Future> process = new ArrayList();

	private static class OrderExecutor implements Callable {

		int id = 0;
		int count = 0;

		private OrderExecutor(int id) {

			this.id = id;
		}

		@Override
		public Object call() {

			while (count++ < 5) {
				try {
					Thread.sleep(new Random(System.currentTimeMillis() % 100).nextInt(100));
					System.out.println("order:" + id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return id;
		}
	}

	public static void main(String[] args) {

		System.out.println("max order"+MAX_NUMBER);
		for(int i = 0; i < MAX_NUMBER; i++){
			submitOrder(i);
		}
		new Thread(new EvilThread(process)).start();
		System.out.println("cancelling random");
		try{
			executorService.awaitTermination(10, TimeUnit.SECONDS);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("check status");
		int count = 0;
		for(Future f : process){
			if(f.isCancelled()){
				count ++;
			}
		}
		System.out.println("canceled sum:"+count);
		executorService.shutdown();
	}

	private static void submitOrder(int id){
		Callable<Integer> callable = new OrderExecutor(id);
		process.add(executorService.submit(callable));
	}

}

class EvilThread implements Runnable{
	private List<Future> process;

	EvilThread(List<Future> process) {

		this.process = process;
	}

	@Override public void run() {
		Random kill = new Random(System.currentTimeMillis() % 100);
		for(int i = 0; i< 100; i++){
			int idx = kill.nextInt(StocksOrderProcessor.MAX_NUMBER);
			boolean cancel = process.get(idx).cancel(true);
			if(cancel){
				System.out.println("cancel succes-->"+idx);
			}
			else{
				System.out.println("cancel fail-->"+idx);
			}
			try {
				Thread.sleep(kill.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
