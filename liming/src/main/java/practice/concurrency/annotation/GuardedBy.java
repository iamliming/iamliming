package practice.concurrency.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-3-18 下午5:33
 */
@Target({ ElementType.FIELD, ElementType.METHOD})
public @interface GuardedBy {
	String value();
}
