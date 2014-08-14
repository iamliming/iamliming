package concurrent.blockqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-12 14:40
 * @id $Id$
 */
public class BlockQueueTest {

	public static void main(String[] args) {

		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

		Runnable product = () ->{
			for(int i = 0; i <  20; i++){
				String cake = "cake"+i;
				try {
					System.out.println("product input "+cake);
					queue.put(cake);
				} catch (InterruptedException e) {
					System.out.println();
					e.printStackTrace();
				}
			}
		};

		Runnable comsumber = () ->{
			for(;;){
				try {
					String cake = queue.take();
					System.out.println("comsumer:"+cake);
					if(cake.equals("cake19")){
						return;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};

		ExecutorService service = Executors.newFixedThreadPool(2);
		List<Runnable> tasks = new ArrayList<>();
//		tasks.add(product);
//		tasks.add(comsumber);
		service.submit(product);
		service.submit(comsumber);
		service.shutdown();
	}

}
