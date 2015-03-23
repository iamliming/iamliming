package practice.concurrency.five;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * using FutureTask to preload data that is needed later
 * @author liming
 * @version 2.2.7
 * @date 15-3-20 下午2:18
 */
public class Preloader {
	private FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
		@Override
		public ProductInfo call () throws Exception {
			return loadProductinfo();
		}
	});

//	public ProductInfo get(){
//		Thread thread = new Thread(future);
//		thread.start();
//		try {
//			return future.get();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//	}

	private ProductInfo loadProductinfo () {
		return null;
	}

}

interface ProductInfo {}
