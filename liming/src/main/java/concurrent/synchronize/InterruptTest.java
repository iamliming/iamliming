package concurrent.synchronize;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-13 1:49
 * @id $Id$
 */
public class InterruptTest {

	public static void main(String[] args) throws InterruptedException {
		Runnable work = ()->{
			long i = 1;
			while(true){
				long j;
				for(j = 2; j < i; j++){
					long n = i % j;
					if(n == 0)
						break;
				}
				if(i == j){
					System.out.println(" "+i);
				}
				i++;
				if(Thread.interrupted()){
					System.out.println("working stop!");
					Thread.currentThread().interrupt();
					return;
				}
			}
		};

		Runnable lazyWork =  ()->{
			try{
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				System.out.println("Lazy worer:" + e.toString());
			}
		};

		Thread generator = new Thread(work);
		generator.start();
		Thread.sleep(1000);
		generator.interrupt();
		Thread.sleep(1000);
		if(generator.isInterrupted()){
			System.out.println("yes!");
		}
		else{
			System.out.println("no!");
		}

		Thread lazy = new Thread(lazyWork);
		lazy.start();
		Thread.sleep(1000);
		lazy.interrupt();
	}
}

