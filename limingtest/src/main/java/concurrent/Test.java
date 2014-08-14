package concurrent;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-07 20:43
 * @id $Id$
 */
public class Test {

	public static void main(String[] args) throws InterruptedException {
		int i = 1;
		for (;;){
			if(i++ < 100){
				System.out.println(i);
				Thread.sleep(100);
				continue;
			}
			System.out.println("over");
			break;
		}
	}
}
