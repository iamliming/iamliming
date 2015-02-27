package iterator.tree;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author liming
 * @version 2.2.7
 * @date 15-2-27 上午11:32
 */
public class TreeSearch {

	private Node root = new Node("root");
	private Stack<Node> stack = new Stack<Node>();
	private Queue<Node> queue = new ArrayBlockingQueue<Node>(100);

	private void makeTree(){
		Node childB = new Node("B");
		Node childC = new Node("C");
		Node childD = new Node("D");
		Node childE = new Node("E");
		Node childF = new Node("F");
		Node childG = new Node("G");
		root.setLeft(childB);
		root.setRight(childC);
		childB.setLeft(childD);
		childB.setRight(childE);
		childC.setLeft(childF);
		childC.setRight(childG);
	}


	public void deepFirstSearch(){
		stack.push(root);
		while(!stack.isEmpty()){
			Node node = pushAndAdd();
			System.out.println(node.getName());
		}
	}
	public void breadthFirstSearch(){

		queue.offer(root);
		while(!queue.isEmpty()){
			Node node = pollAndOffer();
			System.out.println(node.getName());
		}
	}

	private Node pollAndOffer(){
		Node node = queue.poll();
		if(node.getLeft() != null) {
			queue.offer(node.getLeft());
		}
		if(node.getRight() != null){
			queue.offer(node.getRight());
		}
		return node;
	}

	private Node pushAndAdd(){
		Node node = stack.pop();
		if(node.getLeft() != null) {
			stack.push(node.getLeft());
		}
		if(node.getRight() != null){
			stack.push(node.getRight());
		}
		return node;
	}

	public static void main(String[] args) {
		TreeSearch search = new TreeSearch();
		search.makeTree();
		search.deepFirstSearch();
		System.out.println("-------------广度优先--------------");
		search.breadthFirstSearch();
	}
}
