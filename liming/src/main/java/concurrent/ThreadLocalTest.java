package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-2-28 下午2:34
 */
public class ThreadLocalTest extends Thread{
	private static ThreadLocal<Info> threadLocal = new ThreadLocal<Info>(){

		@Override
		protected Info initialValue() {

			return InfoManager.getInfo();
		}
	};

	@Override
	public void run() {

		System.out.println(threadLocal.get().getName());
	}

	public static void main(String[] args) {
		ThreadLocalTest t1 = new ThreadLocalTest();
		ThreadLocalTest t2 = new ThreadLocalTest();
		ThreadLocalTest t3 = new ThreadLocalTest();
		ThreadLocalTest t4 = new ThreadLocalTest();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
class TheadLocalClz {
	private static ThreadLocal<Info> threadLocal = new ThreadLocal<Info>(){

		@Override
		protected Info initialValue() {

			return InfoManager.getInfo();
		}
	};
}
class Info {
	private String name;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}
}

class InfoManager{
	private static AtomicInteger atomicInteger = new AtomicInteger(1);
	public static Info getInfo(){
		Integer i = atomicInteger.getAndIncrement();
		Info info = new Info();
		info.setName("number:"+i);
		return info;
	}
}

