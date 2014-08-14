package concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-06 14:04
 * @id $Id$
 */
public class DeadLock {

	public static void main(String[] args) throws InterruptedException {
		//		Friend f = new Friend("Tony");
		//		Friend b = new Friend("Mike");

//		FriendUsingLock f = new FriendUsingLock("Tony");
//		FriendUsingLock b = new FriendUsingLock("Mike");

		FriendCanBow f = new FriendCanBow("Tony");
		FriendCanBow b = new FriendCanBow("Mike");

		Runnable t = () -> {
			f.bow(b);
		};
		Runnable t1 = () -> {
			b.bow(f);
		};
		Thread threadA = new Thread(t, "Tony");
		Thread threadB = new Thread(t1, "Mike");

		threadA.start();
		threadB.start();


//		final ExecutorService executor = Executors.newScheduledThreadPool(2);
//		executor.execute(threadA);
//		executor.execute(threadB);
//		executor.awaitTermination(4, TimeUnit.SECONDS);
//		executor.shutdownNow();
	}
}

class Friend {

	private String name;

	Friend(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	public synchronized void bow(Friend f) {

		System.out.println(f.getName() + ":bow");
		f.bowOver(this);
	}

	public synchronized void bowOver(Friend f) {

		System.out.println(f.getName() + "bowOver");
	}
}

class FriendUsingLock {

	private Lock lock = new ReentrantLock();
	private String name;

	FriendUsingLock(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	private boolean checkLock(FriendUsingLock f) {

		boolean isLocked = this.lock.tryLock();
		boolean isFriendLocked = f.lock.tryLock();

		if (!isLocked && isFriendLocked) {
			f.lock.unlock();
			System.out.println(Thread.currentThread().getName() + ":unlockFriend:--->" + f.getName());
		} else if (isLocked && !isFriendLocked) {
			this.lock.unlock();
			System.out.println(Thread.currentThread().getName() + ":unlockSelf:--->" + this.getName());
		}

		return isLocked && isFriendLocked;
	}

	public void bow(FriendUsingLock f) {

		if (checkLock(f)) {
			try {
				System.out.println(Thread.currentThread().getName() + "---->" + f.getName() + ":bow");
				f.bowOver(this);
			} finally {
				this.lock.unlock();
				f.lock.unlock();
			}

		}
	}

	public void bowOver(FriendUsingLock f) {

		System.out.println(Thread.currentThread().getName() + "---->" + f.getName() + ":bowOver");
	}
}

/**
 * 至少可以约到,并且出门
 */
class FriendCanBow {

	private Lock lock = new ReentrantLock();
	private String name;

	FriendCanBow(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	private boolean checkLock(FriendCanBow f) {

		boolean isLocked = this.lock.tryLock();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean isFriendLocked = f.lock.tryLock();

		if(!isLocked && !isFriendLocked){
			return false;
		}
		if(isLocked && isFriendLocked){
			return true;
		}

		if (!isLocked && isFriendLocked) {
			f.lock.unlock();
			System.out.println(Thread.currentThread().getName() + ":unlockFriend:--->" + f.getName());
		} else if (isLocked && !isFriendLocked) {
			this.lock.unlock();
			System.out.println(Thread.currentThread().getName() + ":unlockSelf:--->" + this.getName());
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return checkLock(f);
	}

	public void bow(FriendCanBow f) {

		if (checkLock(f)) {
			try {
				System.out.println(Thread.currentThread().getName() + "---->" + f.getName() + ":bow");
				f.bowOver(this);
			} finally {
				this.lock.unlock();
				f.lock.unlock();
			}

		}
	}

	public void bowOver(FriendCanBow f) {

		System.out.println(Thread.currentThread().getName() + "---->" + f.getName() + ":bowOver");
	}
}
