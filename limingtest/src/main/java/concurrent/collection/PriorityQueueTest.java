package concurrent.collection;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-05 17:35
 * @id $Id$
 */

public class PriorityQueueTest
{
	public static void main(String[] args)
	{
		Comparator<String> comparator = (x, y) ->{
			return (x.length() - y.length());
		};
		PriorityQueue<String> queue =
				new PriorityQueue<String>(10,comparator);
		queue.add("short");
		queue.add("very long indeed");
		queue.add("medium");
		queue.add("abc");
		queue.add("lllllllll");
		while (queue.size() != 0)
		{
			System.out.println(queue.remove());
		}
	}
}

