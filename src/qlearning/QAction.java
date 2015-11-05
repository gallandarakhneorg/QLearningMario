package qlearning;

public class QAction extends Counter<QAction> implements QBase {

	private static final long serialVersionUID = 3721996842862787542L;

	@Override
	public int toInt() {
		return getID();
	}

}
