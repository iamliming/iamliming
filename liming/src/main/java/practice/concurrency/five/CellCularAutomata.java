package practice.concurrency.five;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-3-20 下午3:01
 */
public class CellCularAutomata {

}

interface Board {
	int getMaxX();
	int getMaxY();
	int getValue(int x, int y);
	int setNewValue(int x, int y, int value);
	void commitNewValues();
	boolean hasConverged();
	void waitForConvergence();
	Board getSubBoard(int numPartitions, int index);
}
