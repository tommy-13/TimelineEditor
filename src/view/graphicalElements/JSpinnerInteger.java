package view.graphicalElements;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import si.behaviour.Behaviour;
import si.nextIntCalculator.NextIntCalculator;

/**
 * A JSpinner with lower and upper bounds.
 * @author tommy
 *
 */
@SuppressWarnings("serial")
public class JSpinnerInteger extends JSpinner implements ChangeListener {

	private Behaviour 		  behaviour;
	private NextIntCalculator nextIntCalculator;
	
	
	public JSpinnerInteger(Behaviour changeBehaviour, NextIntCalculator nextIntCalculator) {
		super();
		this.behaviour = changeBehaviour;
		this.nextIntCalculator = nextIntCalculator;
		this.setValue(nextIntCalculator.getCurrentValue());
		this.addChangeListener(this);
	}
	
	public void setValue(int value) {
		int val = nextIntCalculator.setCurrentValue(value);
		super.setValue(val);
	}

	public int getInt() {
		return (int) getValue();
	}
	public void setUpperBound(int upperBound) {
		nextIntCalculator.setUpperBound(upperBound);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this) {
			setValue(getInt());
			behaviour.execute(getInt());
		}
	}
	
}
