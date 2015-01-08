package ai.search;

import java.util.Comparator;

public class NodeThreeComparator implements Comparator<AStarThreeNode> {

	@Override
	public int compare(AStarThreeNode node1, AStarThreeNode node2) {
		return (int) (node1.getF() - node2.getF());
	}
}