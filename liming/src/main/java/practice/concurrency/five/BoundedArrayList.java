package practice.concurrency.five;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 有界的ArrayList
 * 计数信号量(Counting Semaphore)用来控制同时访问某一个特定资源的操作数量,或者同时执行某个特定操作的数量
 * 计数器信号量还可以实现某种资源池,或者对容器增加边界
 * acquire()将阻塞直到获取一个许可信号量
 * release()方法将返回一个许可信号量,相当于增加一个信号量
 * Semaphore的信号量数量并不受限于创建的数量
 *
 * @author liming
 * @version 2.2.7
 * @date 15-3-20 下午2:34
 */
public class BoundedArrayList<T> {

	private final List<T> list;
	private final Semaphore semaphore;

	public BoundedArrayList (int bound) {

		list = Collections.synchronizedList(new ArrayList<T>());
		semaphore = new Semaphore(bound);
	}

	/**
	 * 先获取一个信号量
	 * 如果增加失败则返回一个信号量
	 */
	public boolean add (T t) throws InterruptedException {
		semaphore.acquire();
		boolean b = false;
		try {
			b = list.add(t);
			return b;
		} finally {
			if (!b) {
				semaphore.release();
			}
		}
	}
	public boolean remove(T t){
		boolean b = false;
		try{
			b = list.remove(t);
			return b;
		}finally {
			if(b){
				semaphore.release();
			}
		}
	}

}

