package ai.search;

import java.util.Comparator;

public class NodeComparator implements Comparator<AStarNode> {

	@Override
	public int compare(AStarNode node1, AStarNode node2) {
		return (int) (node1.getF() - node2.getF());
	}
}