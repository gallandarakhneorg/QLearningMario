package fr.utbm.tc.qlearningmario.qlearning;

import java.util.Comparator;

public class QActionNumberComparator implements Comparator<QAction> {
	@Override
	public int compare(QAction a, QAction b) {
		return a.toInt() - b.toInt();
	}
}
