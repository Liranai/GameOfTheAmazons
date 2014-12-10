package ai.search;

import java.util.Comparator;

public class NodeTwoComparator implements Comparator<AStarTwoNode> {

	@Override
	public int compare(AStarTwoNode node1, AStarTwoNode node2) {
		return (int) (node1.getF() - node2.getF());
	}
}