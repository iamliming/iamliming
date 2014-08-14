package concurrent.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-08 14:28
 * @id $Id$
 */
public class BucketBallGame {

	public static void main(String[] args) {
		List<Ball> bigBucket = new ArrayList<Ball>();
		List<Ball> smallBucket = new ArrayList<Ball>();
		Lock lock = new ReentrantLock();
		for(int i = 1; i < 20; i++){
			Ball ball = new Ball("bigBall"+i);
			bigBucket.add(ball);
		}
		for(int i = 1; i < 50; i++){
			Ball ball = new Ball("smallBall"+i);
			smallBucket.add(ball);
		}

		BucketRunnabel bigRunnber = new BucketRunnabel(bigBucket,smallBucket,lock,"Mike",3,20l);
		BucketRunnabel smallRunnber = new BucketRunnabel(smallBucket,bigBucket,lock,"Tony",3,10l);


		bigRunnber.setOtherRunnber(smallRunnber);
		smallRunnber.setOtherRunnber(bigRunnber);

		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(bigRunnber);
		service.submit(smallRunnber);
//		smallThread.start();bigThread.start();
	}
}

class BucketRunnabel implements Runnable{
	private String name;
	private Integer number;
	private Lock lock;
	private List<Ball> sourceBucket;
	private List<Ball> destBucket;
	private Long time;
	public Boolean isInterrupted = false;

	private BucketRunnabel otherRunnber;

	//检查锁状态,获取两把锁则继续,否则释放占用的锁,继续争用并且返回false

	BucketRunnabel(List<Ball> balls,List<Ball> destBucket,Lock lock,String name,Integer number,Long time) {
		this.name = name;
		this.sourceBucket = balls;
		this.destBucket = destBucket;
		this.lock = lock;
		this.number = number;
		this.time = time;
	}


	@Override public void run() {
		for(;!isInterrupted;){
			//检查锁
			boolean canTransfer = lock.tryLock();
			if(canTransfer) {
				//开始搬运
				for(int i = 0; i < number; i++){
					//如果都搬完了则发出胜利宣言,并中断对方的搬运.
					if(sourceBucket.size() == 0){
						System.out.println(name + ":------->win!");
						otherRunnber.isInterrupted = true;
						lock.unlock();
						return;
					}
					Ball ball = sourceBucket.remove(0);
					destBucket.add(ball);
					System.out.println(name+"---->transfer-->"+ball);
				}
				//搬完后释放锁,再次争用
				lock.unlock();
			}
			//搬完后休息一段时间,没搬运也休息一段时间再进行搬运
			try {
				Thread.sleep((long) (Math.random()*100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}


	public void setOtherRunnber(BucketRunnabel otherRunnber) {

		this.otherRunnber = otherRunnber;
	}
}


class Ball {
	private String name;

	Ball(String name) {

		this.name = name;
	}

	@Override public String toString() {

		return "Ball{" +
				"name='" + name + '\'' +
				'}';
	}
}
