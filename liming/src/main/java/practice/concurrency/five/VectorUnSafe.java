package practice.concurrency.five;

import java.util.Vector;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-3-19 下午2:22
 */
public class VectorUnSafe {
	public static Object getLast(Vector list){
		synchronized (list) {
			int last = list.size() - 1;
			if (last >= 0) {
				System.out.println("get " + last);
				return list.get(last);
			} else {
				return null;
			}
		}
	}

	public static void deleteLast(Vector list){
		synchronized (list) {
			int last = list.size() - 1;
			if (last >= 0) {
				list.remove(last);
			}
		}
	}

	public static void main(String[] args) {
		Vector list = new Vector();
		for(int i = 0; i < 1000; i++) {
			Object obj = new Object();
			list.add(obj);
		}
		new Thread(){
			@Override
			public void run() {
				while(true) {
					VectorUnSafe.getLast(list);
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run() {
				while(true) {
					VectorUnSafe.deleteLast(list);
				}
			}
		}.start();

	}
}
