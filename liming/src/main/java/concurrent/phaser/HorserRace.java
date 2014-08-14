package concurrent.phaser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-13 15:35
 * @id $Id$
 */
public class HorserRace {

	private static final Phaser manager = new Phaser(1);

	private static class RaceMonitor implements Runnable{

		@Override public void run() {
			while(true){
				System.out.println("number ready to run:"+manager.getArrivedParties());
				try{
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		Thread raceMonitor = new Thread(new RaceMonitor());
		raceMonitor.start();
		new HorserRace().manageRace();

	}

	public void manageRace(){

		List<Horse> horses = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			horses.add(new Horse());
		}
		runRace(horses);
	}

	private void runRace(Iterable<Horse> team){
		for(Horse horse :team){
			manager.register();
			new Thread() {

				@Override public void run() {

					try {
						Thread.sleep(new Random().nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(horse.toString() + ",wait all horses");
					manager.arriveAndAwaitAdvance();
					horse.run();
				}
			}.start();
		}
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("start race");
		manager.arriveAndDeregister();
	}
}

class Horse implements Runnable{
	private static final AtomicInteger idSource = new AtomicInteger();
	private final int id = idSource.incrementAndGet();

	@Override public void run() {

		System.out.println(toString()+"->running");
	}

	@Override public String toString() {

		return "Horse{" +
				"id=" + id +
				'}';
	}

}
