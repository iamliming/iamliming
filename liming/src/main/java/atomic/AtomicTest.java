package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-2-26 下午4:27
 */
public class AtomicTest {
	private static AtomicInteger nextHashCode =
			new AtomicInteger();

	/**
	 * The difference between successively generated hash codes - turns
	 * implicit sequential thread-local IDs into near-optimally spread
	 * multiplicative hash values for power-of-two-sized tables.
	 */
	private static final int HASH_INCREMENT = 0x61c88647;

	/**
	 * Returns the next hash code.
	 */
	private static int nextHashCode() {
		return nextHashCode.getAndAdd(HASH_INCREMENT);
	}

	public static void main(String[] args) {

		System.out.println(HASH_INCREMENT);
        System.out.println();
        System.out.println(nextHashCode());
		System.out.println(nextHashCode());
		System.out.println(nextHashCode());
		System.out.println(nextHashCode());
		System.out.println(nextHashCode());
		System.out.println(nextHashCode());
	}
}
