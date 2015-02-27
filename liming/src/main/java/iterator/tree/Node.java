package iterator.tree;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-2-27 上午11:31
 */
public class Node {
	private String name;
	private Node left;
	private Node right;

	public Node(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Node getLeft() {

		return left;
	}

	public void setLeft(Node left) {

		this.left = left;
	}

	public Node getRight() {

		return right;
	}

	public void setRight(Node right) {

		this.right = right;
	}
}
