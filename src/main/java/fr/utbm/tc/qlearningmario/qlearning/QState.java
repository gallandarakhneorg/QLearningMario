package fr.utbm.tc.qlearningmario.qlearning;

public class QState extends Counter<QState> implements QBase {

	private static final long serialVersionUID = -1506512375211501871L;

	@Override
	public int toInt() {
		return getID();
	}

	@Override
	public QState clone() {
		QState o = null;
		try {
			o = ((QState) super.clone());
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}

		return o;
	}
}
