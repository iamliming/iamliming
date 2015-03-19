package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-2-28 上午9:29
 */
class Widget {
	Lock lock = new ReentrantLock();
	public void dosomething(){
		lock.lock();
		System.out.println("widget!");
		lock.unlock();
	}
}

public class LoggingWidget extends Widget {
	@Override
	public synchronized void dosomething(){
		super.dosomething();
		System.out.println(" logging widget!");
	}

	public static void main(String[] args) {
		LoggingWidget lw = new LoggingWidget();
		lw.dosomething();
//		Thread thread = new Thread()
	}
}
