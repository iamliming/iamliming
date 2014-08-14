package concurrent.futuretask;

import java.util.concurrent.Callable;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-07 21:35
 * @id $Id$
 */
public class MyCallable implements Callable<String> {

	private long waitTime;

	public MyCallable(int timeInMillis){
		this.waitTime=timeInMillis;
	}
	@Override
	public String call() throws Exception {
		Thread.sleep(waitTime);
		//return the thread name executing this callable task
		return Thread.currentThread().getName();
	}

}
