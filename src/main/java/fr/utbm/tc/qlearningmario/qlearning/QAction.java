package fr.utbm.tc.qlearningmario.qlearning;

public class QAction extends Counter<QAction> implements QBase {

	private static final long serialVersionUID = 3721996842862787542L;

	@Override
	public int toInt() {
		return getID();
	}

    @Override
	public QAction clone() {
    	QAction o = null;
		try {
			o = ((QAction) super.clone());
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}

		return o;
	}
}
