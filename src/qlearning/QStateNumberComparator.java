package qlearning;

import java.util.Comparator;

public class QStateNumberComparator implements Comparator<QState>{
	@Override
	public int compare(QState a, QState b) {
		return a.toInt() - b.toInt();
	}
}
