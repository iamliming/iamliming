package concurrent.comcurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-12 10:50
 * @id $Id$
 */
public class BlockingQueueTest {

	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<String> messages = new ArrayBlockingQueue<String>(10);
		for(int i = 0; i < 20; i++){
			messages.put("" + i);
		}

		System.out.println(messages);
	}
}
