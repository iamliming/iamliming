package concurrent.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-07 21:35
 * @id $Id$
 */
public class FutureTaskExample {

	public static void main(String[] args) throws InterruptedException {
		MyCallable callable1 = new MyCallable(5000);
		MyCallable callable2 = new MyCallable(2000);
		FutureTask<String> task1 = new FutureTask<String>(callable1);
		FutureTask<String> task2 = new FutureTask<String>(callable2);
		ExecutorService service = Executors.newFixedThreadPool(3);
		service.submit(task1);
		service.submit(task2);
		String ss = null;
		for(;;Thread.sleep(100)){
			try {
				if (task1.isDone() && task2.isDone()) {
					service.shutdown();
					System.out.println("done");
					break;
				} else if (task1.isDone()) {
					String s = null;
					s = task1.get();
					System.out.println("task1:" + s);
				}else if((ss = task2.get(200L, TimeUnit.MILLISECONDS)) != null){
					System.out.println("FutureTask2 output=" + ss);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}
