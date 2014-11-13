package ai.search;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node node1, Node node2) {
		return (int) (node1.getF() - node2.getF());
	}
}