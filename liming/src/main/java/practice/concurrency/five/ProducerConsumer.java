package practice.concurrency.five;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-3-20 上午9:51
 */
public class ProducerConsumer {

	static class FileCrawl implements Runnable {

		private final BlockingQueue<File> fileQueue;
		private final FileFilter fileFilter;
		private final File root;

		public FileCrawl(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {

			this.fileQueue = fileQueue;
			this.fileFilter = new FileFilter() {

				@Override public boolean accept(File file) {

					return file.isDirectory() || fileFilter.accept(file);
				}
			};
			this.root = root;
		}

		@Override public void run() {

			try {
				crawl(root);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void crawl(File root) throws InterruptedException {

			File[] files = root.listFiles(fileFilter);
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						crawl(file);
					} else {
						if (!alreadyIndexed(file))
							fileQueue.put(file);
					}
				}
			}
		}

		private boolean alreadyIndexed(File file) {

			return false;
		}
	}

	static class Indexer implements Runnable{

		private final BlockingQueue<File> fileQueue;

		Indexer(BlockingQueue<File> fileQueue) {

			this.fileQueue = fileQueue;
		}

		public void index(File file){
			//do index
			System.out.println("index:" + file.getName());
		}

		@Override
		public void run() {
			while(true){
				try {
					File file = fileQueue.take();
					index(file);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		BlockingQueue<File> fileQueue = new ArrayBlockingQueue<File>(10);
		FileFilter fileFilter = new FileFilter() {

			@Override
			public boolean accept (File file) {

				return true;
			}
		};

		File file = new File("/home/tony/dev/githubliming/iamliming");
		new Thread(new FileCrawl(fileQueue,fileFilter,file)).start();
		new Thread(new Indexer(fileQueue)).start();

	}

}
