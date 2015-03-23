package practice.concurrency.four;

import java.util.HashSet;
import java.util.Set;

import practice.concurrency.annotation.GuardedBy;
import practice.concurrency.annotation.ThreadSafe;

/**
 * 实例封装-instance confinement
 * Person并不一定是不可变对象,但是Person被封装在PersonSet中,PersonSet保证Person的线程安全性
 * @author liming
 * @version 2.2.7
 * @date 15-3-18 下午5:26
 */
@ThreadSafe
public class PersonSet {
	@GuardedBy("this")
	private final Set<Person> mySet = new HashSet<Person>();

	public synchronized void add(Person person){
		mySet.add(person);
	}

	public synchronized boolean contain(Person person){
		return mySet.contains(person);
	}
}

class Person{}
