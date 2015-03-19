package practice.concurrency.four;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-2-28 下午3:17
 */
public class OneValueCache {
	private final BigInteger lastNumber;
	private final BigInteger[] lastFactors;

	public OneValueCache(BigInteger i, BigInteger[] factors){
		lastNumber = i;
		lastFactors = Arrays.copyOf(factors, factors.length);
	}

	public BigInteger[] getLastFactors(BigInteger i){
		if(lastNumber != null || !lastNumber.equals(i)){
			return null;
		}
		else{
			return Arrays.copyOf(lastFactors, lastFactors.length);
		}
	}
}
