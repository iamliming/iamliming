package concurrent.collection;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-06 14:42
 * @id $Id$
 */
public class ProductComsumer {

	public static void main(String[] args) throws InterruptedException {

		SomeThing someThing = new SomeThing();
		Runnable product = () -> {
			int i = 0;
			while (i++ < 10) {
				Cake cake = new Cake(i, "cake" + i);
				someThing.put(cake);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable comsumer1 = () -> {
			String cakeName = "";
			while (!cakeName.equals("cake10")) {
				cakeName = someThing.glance();
			}
			System.out.println("Comsumber1 finish!");
		};
		Runnable comsumer2 = () -> {
			String cakeName = "";
			while (!cakeName.equals("cake10")) {
				cakeName = someThing.glance();
			}
			System.out.println("Comsumber2 finish!");
		};

		new Thread(product, "product").start();
		new Thread(comsumer1, "comsumer1").start();
		new Thread(comsumer2, "comsumer2").start();

	}

}

class SomeThing {

	static final Integer PRODUCT = 1;
	static final Integer COMSUMER = 2;
	private Cake cake;
	private Integer status = PRODUCT;

	public synchronized String glance() {

		try {
			while (status.intValue() == PRODUCT.intValue()) {
				wait();
			}
			System.out.println(Thread.currentThread().getName() + "<----" + cake);
			status = PRODUCT;
			String cakeName = cake.getName();
			if(cakeName.equals("cake10")){
				status = COMSUMER;
			}
			notifyAll();
			return cakeName;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}

	public synchronized void put(Cake cake) {

		try {
			while (status.intValue() == COMSUMER.intValue()) {
				wait();
			}
			this.cake = cake;
			System.out.println(Thread.currentThread().getName() + "---->" + cake);
			status = COMSUMER;
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
