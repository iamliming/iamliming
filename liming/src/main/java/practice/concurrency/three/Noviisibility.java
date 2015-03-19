package practice.concurrency.three;

/**
 * 内存可见性.
 * 当读操作和写操作在不同的线程中执行时,无法确保执行读操作的线程适时看到其他线程写入的值,有时甚至是不可能的事情
 * 为了确保多个线程之间对内存写入操作的可见性,必须使用同步机制
 * 读线程可能一直循环下去,因为读线程看不到写入的ready值
 * 更奇怪的现象是,Novisibility会输出0,因为读线程可能看到了写入的ready值,但看不到写入的number值,这种现象叫做"重排序(Reordering)
 * 当主线程首先写入number,然后写入ready,那么读线程看到的顺序可能与写入的顺序完全相反
 * @author liming
 * @version 2.2.7
 * @date 15-2-28 上午9:38
 */
public class Noviisibility {
	private static  boolean ready;
	private static  int number;

	private static class ReadThread extends Thread {
		public void run(){
			while(!ready){
				Thread.yield();
				System.out.println(number);
			}
		}
	}

	public static void main(String[] args) {
		new ReadThread().start();
		number = 42;
		ready = true;
		System.out.println("main:" + number);
	}
}
