package concurrent.concurrence;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-06 23:39
 * @id $Id$
 */
public class CallableTest {

	public static Callable<int[]> multlyCallabe(int[] a, int[] b) {

		Callable<int[]> c = () -> {
			int[] rst = new int[a.length + b.length];
			System.arraycopy(a, 0, rst, 0, a.length);
			System.arraycopy(b, 0, rst, a.length, b.length);
			return rst;
		};
		return c;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		int[] a = new int[] { 1, 2 };
		int[] b = new int[] { 3, 4 };
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Callable<int[]> task = multlyCallabe(a, b);
		Future<int[]> future = executorService.submit(task);
		while (!future.isDone()) {
			Thread.sleep(1000);
		}
		int[] rst = future.get();
		for (int i : rst) {
			System.out.println(i);
		}
	}
}
